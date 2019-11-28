package cn.tcualhp.tech_kg.service;

import cn.tcualhp.tech_kg.process.Classification;
import cn.tcualhp.tech_kg.process.Question;
import cn.tcualhp.tech_kg.process.QuestionList;
import cn.tcualhp.tech_kg.process.Vocabulary;
import cn.tcualhp.tech_kg.utils.LoadJsonUtil;
import cn.tcualhp.tech_kg.utils.TermUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.classification.NaiveBayes;
import org.apache.spark.mllib.classification.NaiveBayesModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author lihepeng
 * @description 智能问答测试类
 * @date 2019-11-22 19:07
 **/
public class QuestionServiceDemo {

    /**
     * 分类标签号和问句模板对应表
     */
    Map<Double, String> questionsPattern;

    /**
     * Spark贝叶斯分类器
     */
    NaiveBayesModel nbModel;

    /**
     * 词语和下标的对应表   == 词汇表
     */
    Map<String, Integer> vocabulary;

    /**
     * 关键字与其词性的map键值对集合 == 句子抽象
     */
    Map<String, String> abstractMap;

    /**
     * 指定问题question及字典的txt模板所在的根目录
     */
//    String rootDirPath = "D:/HanLP/data";

    /**
     * 分类模板索引
     */
    int modelIndex = 0;

    public QuestionServiceDemo() throws Exception {
        questionsPattern = loadQuestionsPattern();
        vocabulary = loadVocabulary();
        nbModel = loadClassifierModel();
    }

    public ArrayList<String> analyQuery(String queryString) throws Exception {

        /**
         * 打印问句
         */
        System.out.println("原始句子：" + queryString);
        System.out.println("========HanLP开始分词========");

        /**
         * 抽象句子，利用HanPL分词，将关键字进行词性抽象
         */
        String abstr = queryAbstract(queryString);
        System.out.println("句子抽象化结果：" + abstr);// nm 的 导演 是 谁

        /**
         * 将抽象的句子与spark训练集中的模板进行匹配，拿到句子对应的模板
         */
        String strPatt = queryClassify(abstr);
        System.out.println("句子套用模板结果：" + strPatt); // nm 制作 导演列表


        /**
         * 模板还原成句子，此时问题已转换为我们熟悉的操作
         */
        String finalPattern = queryExtenstion(strPatt);
        System.out.println("原始句子替换成系统可识别的结果：" + finalPattern);// 但丁密码 制作 导演列表

        ArrayList<String> resultList = new ArrayList<String>();
        resultList.add(String.valueOf(modelIndex));
//        String[] finalPattArray = finalPattern.split(" ");
//        resultList.addAll(Arrays.asList(finalPattArray));
        return resultList;
    }

    public String queryAbstract(String querySentence) {
        /**
         * 句子抽象化
         */
        Segment segment = HanLP.newSegment().enableCustomDictionary(true).enableNameRecognize(true);
        List<Term> terms = segment.seg(querySentence);
        String abstractQuery = "";
        abstractMap = new HashMap<String, String>();
        /**
         * nr 人名词性这个 词语出现的频率
         */
        int nrCount = 0;
        for (Term term : terms) {
            String word = term.word;
            System.out.println(term.toString());
            if (TermUtil.isWordNatureEquals(term, "nr") && nrCount == 0) {
                /**
                 *  nr 人名
                 */
                abstractQuery += "nr";
                abstractMap.put("nr", word);
                nrCount++;
            }else if (TermUtil.isWordNatureEquals(term, "nr") && nrCount == 1){
                /**
                 * 第二次出现人名
                 */
                abstractQuery += "n2r";
                abstractMap.put("n2r", word);
                nrCount++;
            }else if (TermUtil.isWordNatureEquals(term, "m")){
                /**
                 * 数字
                 * 注意目前是用作年份，但也许会和年龄冲突
                 * 这个问题可能需要用分类器的不同问题模板来弥补
                 */
                abstractQuery += "m";
                abstractMap.put("m", word);
            }else if (TermUtil.isWordNatureEquals(term, "wk")){
                /**
                 * 自定义关键词
                 */
                abstractQuery += "wk";
                abstractMap.put("wk", word);
            }else if (TermUtil.isWordNatureEquals(term, "nt") || TermUtil.isWordNatureEquals(term, "ntu")
            || TermUtil.isWordNatureEquals(term, "ntc") || TermUtil.isWordNatureEquals(term, "nth")
            || TermUtil.isWordNatureEquals(term, "nto") || TermUtil.isWordNatureEquals(term, "nts")){
                /**
                 * 单位，包含很多单位，所以自定义一个词性
                 */
                abstractQuery += "unit";
                abstractMap.put("unit", word);
            }
            else {
                abstractQuery += word + " ";
            }
        }
        System.out.println("========HanLP分词结束========");
        return abstractQuery;
    }

    /**
     * 加载词汇表 == 关键特征 == 与HanLP分词后的单词进行匹配
     *
     * @return
     */
    public Map<String, Integer> loadVocabulary() {
        Map<String, Integer> vocabulary = new HashMap<String, Integer>();
        int index = 1;
        try {
            Set<Vocabulary> vocabularies = new Vocabulary().getVocabularySet("/vocabulary/vocabulary.json");
            for (Vocabulary v : vocabularies) {
                vocabulary.put(v.getValue(), index);
                index += 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vocabulary;
    }

    /**
     * 句子分词后与词汇表进行key匹配转换为double向量数组
     *
     * @param sentence
     * @return
     * @throws Exception
     */
    public double[] sentenceToArrays(String sentence) throws Exception {

        double[] vector = new double[vocabulary.size()];
        /**
         * 模板对照词汇表的大小进行初始化，全部为0.0
         */
        for (int i = 0; i < vocabulary.size(); i++) {
            vector[i] = 0;
        }

        /**
         * HanLP分词，拿分词的结果和词汇表里面的关键特征进行匹配
         */
        Segment segment = HanLP.newSegment();
        List<Term> terms = segment.seg(sentence);
        for (Term term : terms) {
            String word = term.word;
            /**
             * 如果命中，0.0 改为 1.0
             */
            if (vocabulary.containsKey(word)) {
                int index = vocabulary.get(word);
                vector[index] = 1;
            }
        }
        return vector;
    }

    /**
     * 贝叶斯分类器分类的结果，拿到匹配的分类标签号，并根据标签号返回问题的模板
     *
     * @param sentence
     * @return
     * @throws Exception
     */
    public String queryClassify(String sentence) throws Exception {

        double[] testArray = sentenceToArrays(sentence);
        Vector v = Vectors.dense(testArray);

        /**
         * 对数据进行预测predict
         * 句子模板在 spark贝叶斯分类器中的索引【位置】
         * 根据词汇使用的频率推断出句子对应哪一个模板
         */
        double index = nbModel.predict(v);
        modelIndex = (int) index;
        System.out.println("the model index is " + index);
//		Vector vRes = nbModel.predictProbabilities(v);
//		System.out.println("问题模板分类【0】概率："+vRes.toArray()[0]);
//		System.out.println("问题模板分类【13】概率："+vRes.toArray()[13]);
        return questionsPattern.get(index);
    }

    /**
     * 加载问题模板 == 分类器标签
     *
     * @return
     */
    public Map<Double, String> loadQuestionsPattern() {
        Map<Double, String> questionsPattern = new HashMap<Double, String>();
        try {
            JSONObject jsonObject = LoadJsonUtil.getJsonObject("classify/classification.json");
            /**
             * 获取分类数组的字符串
             */
            String classficationsString = jsonObject.getJSONArray("classifications").toString();
            //解析成字符串数组
            List<Classification> classifications = JSONArray.parseArray(classficationsString, Classification.class);
            for (Classification classification : classifications) {
                /**
                 * 放进分类模板
                 */
                questionsPattern.put((double) classification.getId(), classification.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questionsPattern;
    }

    /**
     * Spark朴素贝叶斯(naiveBayes)
     * 对特定的模板进行加载并分类
     * 欲了解Spark朴素贝叶斯，可参考地址：https://blog.csdn.net/appleyk/article/details/80348912
     *
     * @return
     * @throws Exception
     */
    public NaiveBayesModel loadClassifierModel() throws Exception {

        /**
         * 生成Spark对象
         * 一、Spark程序是通过SparkContext发布到Spark集群的
         * Spark程序的运行都是在SparkContext为核心的调度器的指挥下进行的
         * Spark程序的结束是以SparkContext结束作为结束
         * JavaSparkContext对象用来创建Spark的核心RDD的
         * 注意：第一个RDD,一定是由SparkContext来创建的
         *
         * 二、SparkContext的主构造器参数为 SparkConf
         * SparkConf必须设置appname和master，否则会报错
         * spark.master   用于设置部署模式
         * local[*] == 本地运行模式[也可以是集群的形式]，如果需要多个线程执行，可以设置为local[2],表示2个线程 ，*表示多个
         * spark.app.name 用于指定应用的程序名称  ==
         */

        /**
         * 题外话
         * 贝叶斯是谁？
         * 贝叶斯(约1701-1763) Thomas Bayes，英国数学家。
         * 1702年出生于伦敦，做过神甫。
         * 1742年成为英国皇家学会会员。
         * 1763年4月7日逝世。
         * 贝叶斯在数学方面主要研究概率论 == 贝叶斯公式是概率论中较为重要的公式
         */
        SparkConf conf = new SparkConf().setAppName("NaiveBayesTest").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        /**
         * 训练集生成
         * labeled point 是一个局部向量，要么是密集型的要么是稀疏型的
         * 用一个label/response进行关联。在MLlib里，labeled points 被用来监督学习算法
         * 我们使用一个double数来存储一个label，因此我们能够使用labeled points进行回归和分类
         */
        List<LabeledPoint> train_list = new LinkedList<LabeledPoint>();


        final String questionRootDir = "/questions/";
        //问题列表文件名
//        final List<String> questionFileNames = new ArrayList<>();
//        questionFileNames.add("00questionPublish.json");
//        questionFileNames.add("01questionWorkIn.json");
//        questionFileNames.add("02questionCooperation.json");
//        questionFileNames.add("06questionExpertKeywords.json");
//        questionFileNames.add("05questionUnitExperts.json");
//        questionFileNames.add("04questionUnitPapers.json");
//        questionFileNames.add("03questionYearKeywords.json");

        /**
         * 问题列表
         */
        File questionDir = new File("src/main/resources/questions");
        String[] questionFileNames = questionDir.list();

        //加载所有问题模型
        for (String questionFileName : questionFileNames
        ) {
            QuestionList questionList = new QuestionList(questionRootDir + questionFileName);
            List<Question> questions = questionList.getQuestions();
            //将问题列表转换成向量
            for (Question q : questions) {
                double[] array = sentenceToArrays(q.getValue());
                //标签是问题类型
                LabeledPoint train_one = new LabeledPoint(questionList.getQuestionType(), Vectors.dense(array));
                train_list.add(train_one);
            }
        }

        /**
         * SPARK的核心是RDD(弹性分布式数据集)
         * Spark是Scala写的,JavaRDD就是Spark为Java写的一套API
         * JavaSparkContext sc = new JavaSparkContext(sparkConf);    //对应JavaRDD
         * SparkContext	    sc = new SparkContext(sparkConf)    ;    //对应RDD
         */
        JavaRDD<LabeledPoint> trainingRDD = sc.parallelize(train_list);
        NaiveBayesModel nb_model = NaiveBayes.train(trainingRDD.rdd());

        /**
         * 记得关闭资源
         */
        sc.close();

        /**
         * 返回贝叶斯分类器
         */
        return nb_model;

    }

    public String queryExtenstion(String queryPattern) {
        // 句子还原
        Set<String> set = abstractMap.keySet();
        for (String key : set) {
            /**
             * 如果句子模板中含有抽象的词性
             */
            if (queryPattern.contains(key)) {

                /**
                 * 则替换抽象词性为具体的值
                 */
                String value = abstractMap.get(key);
                queryPattern = queryPattern.replace(key, value);
            }
        }
        String extendedQuery = queryPattern;
        /**
         * 当前句子处理完，抽象map清空释放空间并置空，等待下一个句子的处理
         */
        abstractMap.clear();
        abstractMap = null;
        return extendedQuery;
    }

    public static void main(String[] args) {
        try {
            QuestionServiceDemo questionServiceDemo = new QuestionServiceDemo();
            questionServiceDemo.analyQuery("李鹤鹏在哪家单位工作");
            System.out.println("");
            questionServiceDemo.analyQuery("李鹤鹏发表了什么论文");
            System.out.println("");
            questionServiceDemo.analyQuery("李鹤鹏工作于哪个单位");
            System.out.println("");
            questionServiceDemo.analyQuery("李鹤鹏在哪里");
            System.out.println("");
            questionServiceDemo.analyQuery("有哪些论文是李鹤鹏写的");
            System.out.println("");
            questionServiceDemo.analyQuery("李鹤鹏工作的地方在哪里");
            System.out.println("");
            questionServiceDemo.analyQuery("丁健和李鹤鹏合作发表的论文有哪些");
            System.out.println("");
            questionServiceDemo.analyQuery("杭电有哪些人");
            System.out.println("");
            System.out.println("注：因为还没开启自定义词典，下面的分类应该会错");
            questionServiceDemo.analyQuery("2019年关于知识图谱的论文有哪些");
            System.out.println("");
            questionServiceDemo.analyQuery("杭电有哪些论文");
            System.out.println("");
            questionServiceDemo.analyQuery("李鹤鹏发表过的关于知识图谱的论文有哪些");
            System.out.println("");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
