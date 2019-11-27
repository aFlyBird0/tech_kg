package cn.tcualhp.tech_kg.service;

import cn.tcualhp.tech_kg.utils.TermUtil;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import org.apache.spark.mllib.classification.NaiveBayesModel;

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
    String rootDirPath = "D:/HanLP/data";

    /**
     * 分类模板索引
     */
    int modelIndex = 0;

    public ArrayList<String> analyQuery(String queryString) throws Exception {

        /**
         * 打印问句
         */
        System.out.println("原始句子："+queryString);
        System.out.println("========HanLP开始分词========");

        /**
         * 抽象句子，利用HanPL分词，将关键字进行词性抽象
         */
        String abstr = queryAbstract(queryString);
        System.out.println("句子抽象化结果："+abstr);// nm 的 导演 是 谁

        /**
         * 将抽象的句子与spark训练集中的模板进行匹配，拿到句子对应的模板
         */
//        String strPatt = queryClassify(abstr);
//        System.out.println("句子套用模板结果："+strPatt); // nm 制作 导演列表
//
//
//        /**
//         * 模板还原成句子，此时问题已转换为我们熟悉的操作
//         */
//        String finalPattern = queryExtenstion(strPatt);
//        System.out.println("原始句子替换成系统可识别的结果："+finalPattern);// 但丁密码 制作 导演列表
//
//
        ArrayList<String> resultList = new ArrayList<String>();
        resultList.add(String.valueOf(modelIndex));
//        String[] finalPattArray = finalPattern.split(" ");
//        resultList.addAll(Arrays.asList(finalPattArray));
        return resultList;
    }

    public  String queryAbstract(String querySentence) {

        // 句子抽象化
        Segment segment = HanLP.newSegment().enableCustomDictionary(true).enableNameRecognize(true);
        List<Term> terms = segment.seg(querySentence);
        String abstractQuery = "";
        abstractMap = new HashMap<String, String>();
        int nrCount = 0; //nr 人名词性这个 词语出现的频率
        for (Term term : terms) {
            String word = term.word;
            System.out.println(term.toString());
            if (TermUtil.isWordNatureEquals(term, "nm")) {        //nm 电影名
                abstractQuery += "nm ";
                abstractMap.put("nm", word);
            } else if (TermUtil.isWordNatureEquals(term, "nr") && nrCount == 0) { //nr 人名
                abstractQuery += "nnt";
                abstractMap.put("nnt", word);
                nrCount++;
            }else if (TermUtil.isWordNatureEquals(term, "nnr") && nrCount == 1) { //nr 人名 再出现一次，改成nnr
                abstractQuery += "nnr ";
                abstractMap.put("nnr", word);
                nrCount++;
            }else if (TermUtil.isWordNatureEquals(term, "x")) {  //x  评分
                abstractQuery += "x ";
                abstractMap.put("x", word);
            } else if (TermUtil.isWordNatureEquals(term, "ng")) { //ng 类型
                abstractQuery += "ng ";
                abstractMap.put("ng", word);
            }
            else {
                abstractQuery += word + " ";
            }
        }
        System.out.println("========HanLP分词结束========");
        return abstractQuery;
    }

    public static void main(String[] args) {
        QuestionServiceDemo questionServiceDemo = new QuestionServiceDemo();
        try {
            questionServiceDemo.analyQuery("将抽象的句子与spark训练集中的模板进行匹配，拿到句子对应的模板,测试张亮打球");
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
