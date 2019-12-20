package cn.tcualhp.tech_kg.service.impl;

import cn.tcualhp.tech_kg.model.Neo4jNode.ExpertNode;
import cn.tcualhp.tech_kg.model.Neo4jNode.PaperNode;
import cn.tcualhp.tech_kg.model.Neo4jNode.UnitNode;
import cn.tcualhp.tech_kg.neo4jRepo.ExpertNodeRepo;
import cn.tcualhp.tech_kg.neo4jRepo.JournalNodeRepo;
import cn.tcualhp.tech_kg.neo4jRepo.PaperNodeRepo;
import cn.tcualhp.tech_kg.neo4jRepo.UnitNodeRepo;
import cn.tcualhp.tech_kg.service.Question2ModelString;
import cn.tcualhp.tech_kg.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author lihepeng
 * @description 智能问答Service
 * @date 2019-11-27 23:15
 **/
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private Question2ModelString question2ModelString;

    @Autowired
    private ExpertNodeRepo expertNodeRepo;

    @Autowired
    private PaperNodeRepo paperNodeRepo;

    @Autowired
    private UnitNodeRepo unitNodeRepo;

    @Autowired
    private JournalNodeRepo journalNodeRepo;

    /**
     * 对 第一个 分类的问题的答案获取
     *
     * @param expertName String 专家姓名
     * @return
     */
//    public List<PaperNode> getAnswerOfModelOne(String expertName) {
//        List<ExpertNode> expertNodes = expertNodeRepo.getExpertNodesByName(expertName);
//        List<PaperNode> paperNodes = new ArrayList<>();
//        if (expertNodes.size() < 1) {
//            return paperNodes;
//        } else {
//            ExpertNode expertNode = expertNodes.get(0);
//        }
//        paperNodes.add(expertNode.getPaperNodes());
//        /**
//         * 这里先简单处理一下
//         */
//        answer = paperNodes.toString();
//        return paperNodes;
//    }
    @Override
    public String magicAnswer(String question) {
        ArrayList<String> reStrings = null;
        try {
            reStrings = question2ModelString.analysisQuery(question);
        } catch (Exception e) {
            e.printStackTrace();
            return "服务器发生错误";
        }
        int modelIndex = Integer.parseInt(reStrings.get(0));
        String answer = null;
        /**
         * 专家姓名
         */
        String expertName = "";

        String expertName2 = "";
        Set<PaperNode> paperNodes = null;
        List<ExpertNode> expertNodes = null;
        ExpertNode expertNode;
        Set<UnitNode> unitNodes = null;
        String title = "";
        String name = "";
        String type = "";
        Double score = 0.0;
        /**
         * 对最终的问题答案进行拼接，注意使用前需要清空 ，setLength(0) 即可
         */
        StringBuffer finalAnswer = new StringBuffer();
        /**
         * 检索的论文的关键词
         */
        String keywords = new String();
        /**
         * 单位/机构 名称
         */
        String unitName = new String();
        /**
         * 论文节点的 list
         */
        List<PaperNode> paperNodeList = new ArrayList<>();
        /**
         * 专家节点的 list
         */
        List<ExpertNode> expertNodeList = new ArrayList<>();
        /**
         * 论文发表年份
         */
        Integer year;
        /**
         * 单位节点的 list
         */
        List<UnitNode> unitNodeList = new ArrayList<>();


        /**
         * 匹配问题模板
         */
        switch (modelIndex) {
            case 0:
                /**
                 * 抽象问题模板：nr 论文
                 */
//                expertName = reStrings.get(1);
//                /**
//                 * 注意这里要判断重名
//                 * 现在只是简单取第一个
//                 * 以后要加如下判断
//                 * 如果人多余一个要询问是哪个
//                 * 如果只有一个直接返回论文
//                 */
//                expertNodes = expertNodeRepo.getExpertNodesByName(expertName);
//                if (expertNodes.size() < 1) {
//                    answer = "无此人";
//                    break;
//                } else {
//                    expertNode = expertNodes.get(0);
//                }
//                paperNodes = expertNode.getPaperNodes();
//                /**
//                 * 这里先简单处理一下
//                 */
//                answer = paperNodes.toString();
//                break;
                expertName = reStrings.get(1);
                finalAnswer.setLength(0);
                finalAnswer.append(expertName);
                finalAnswer.append(" 专家发表的论文有：");
                paperNodeList.clear();
                paperNodeList = paperNodeRepo.getPaperNodesByExpertName(expertName);
                if (paperNodeList.isEmpty()) {
                    answer = "无答案";
                    break;
                } else {
                    for (PaperNode node : paperNodeList) {
                        finalAnswer.append(node.getName());
                        finalAnswer.append(" ");
                    }
                    answer = finalAnswer.toString();
                }
                break;
            case 1:
//                /**
//                 * 抽象问题模板：nr 单位
//                 */
//                expertName = reStrings.get(1);
//                /**
//                 * 注意这里要判断重名
//                 * 现在只是简单取第一个
//                 * 以后要加如下判断
//                 * 如果人多余一个要询问是哪个
//                 * 如果只有一个直接返回论文
//                 */
//                expertNode = expertNodeRepo.getExpertNodesByName(expertName).get(0);
//                unitNodes = expertNode.getUnitNodes();
//                answer = unitNodes.toString();
//                break;
                expertName = reStrings.get(1);
                finalAnswer.setLength(0);
                finalAnswer.append(expertName);
                finalAnswer.append(" 专家就职于 ：");
                unitNodeList.clear();
                unitNodeList = unitNodeRepo.getUnitNodeByExpertName(expertName);
                if (unitNodeList.isEmpty()) {
                    answer = "无答案";
                    break;
                } else {
                    for (UnitNode node : unitNodeList) {
                        finalAnswer.append(node.getUnitName());
                        finalAnswer.append(" ");
                    }
                    answer = finalAnswer.toString();
                }
                break;
            case 2:
                /**
                 * 抽象模板详见classify/classifications.json
                 * 这里最好把case都封装成函数，现在太乱了
                 * 比如所有的case的变量不能重复定义，很坑
                 * 这里是 2 nr n2r 合作论文
                 *
                 */
//                answer = paperNodeRepo.getPaperNodeByCooperation(reStrings.get(1), reStrings.get(2)).toString();
//                break;
                expertName = reStrings.get(1);
                expertName2 = reStrings.get(2);
                finalAnswer.setLength(0);
                finalAnswer.append(expertName);
                finalAnswer.append(" 专家和 ");
                finalAnswer.append(expertName2);
                finalAnswer.append(" 专家合作发表的论文有：");
                paperNodeList.clear();
                paperNodeList = paperNodeRepo.getPaperNodeByCooperation(expertName, expertName2);
                if (paperNodeList.isEmpty()) {
                    answer = "无答案";
                    break;
                } else {
                    for (PaperNode node : paperNodeList) {
                        finalAnswer.append(node.getName());
                        finalAnswer.append(" ");
                    }
                    answer = finalAnswer.toString();
                }
                break;
            case 3:
                /**
                 * 抽象问题模板：m年发表的某关键词的论文/专利有哪些
                 *
                 */
                year = Integer.parseInt(reStrings.get(1).replace("年", ""));
                keywords = reStrings.get(2);
                finalAnswer.setLength(0);
                finalAnswer.append(year.toString());
                finalAnswer.append(" 年 ");
                finalAnswer.append("发表的关于 ");
                finalAnswer.append(keywords);
                finalAnswer.append(" 有 ：");
                paperNodeList.clear();
                keywords = ".*" + keywords + ".*";
                paperNodeList = paperNodeRepo.getPaperNodeByYearAndKeywords(year, keywords);
                System.out.println(paperNodeList.toString());
                if (paperNodeList.isEmpty()) {
                    answer = "无答案";
                    break;
                } else {
                    for (PaperNode node : paperNodeList) {
                        finalAnswer.append(node.getName());
                        finalAnswer.append(" ");
                    }
                    answer = finalAnswer.toString();
                }
                break;
            case 4:
                /**
                 * 某单位的论文/专利有哪些
                 */
                finalAnswer.setLength(0);
                unitName = reStrings.get(1);
                finalAnswer.append(unitName);
                finalAnswer.append(" 有以下论文：");
                paperNodeList.clear();
                paperNodeList = paperNodeRepo.getPaperNodeByUnitHavePaper(unitName);
                if (paperNodeList.isEmpty()) {
                    answer = "无答案";
                    break;
                } else {
                    for (PaperNode node : paperNodeList) {
                        finalAnswer.append(node.getName());
                        finalAnswer.append(" ");
                    }
                    answer = finalAnswer.toString();
                }
                break;
            case 5:
                /**
                 * 某单位 有哪些专家
                 */
                /**
                 * 对先前的数据 StringBuffer 的数据进行清空
                 */
                finalAnswer.setLength(0);
                unitName = reStrings.get(1);
                finalAnswer.append(unitName);
                finalAnswer.append("有:");
                expertNodeList.clear();
                expertNodeList = expertNodeRepo.getExpertNodesByUnitNameContains(unitName);
                if (expertNodeList.isEmpty()) {
                    answer = "无答案";
                    break;
                } else {
                    for (ExpertNode node : expertNodeList) {
                        finalAnswer.append(node.getName());
                        finalAnswer.append(" ");
                    }
                    answer = finalAnswer.toString();
                }
                break;
            case 6:
                /**
                 * 某专家nr 关于 关键字wk 的论文有哪些 ？
                 */
                finalAnswer.setLength(0);
                expertName = reStrings.get(1);
                keywords = reStrings.get(2);
                finalAnswer.append(expertName);
                finalAnswer.append(" 专家的关于 ");
                finalAnswer.append(keywords);
                finalAnswer.append(" 的论文有：");
                paperNodeList.clear();
                paperNodeList = paperNodeRepo.getPaperNodeByExpertNameAndKeywords(keywords, expertName);
                if (paperNodeList.isEmpty()) {
                    answer = "无答案";
                    break;
                } else {
                    for (PaperNode node : paperNodeList) {
                        finalAnswer.append(node.getName());
                        finalAnswer.append(" ");
                    }
                    answer = finalAnswer.toString();
                }
            default:
                break;
        }

        System.out.println(answer);
        /**
         * 以下的 "\\N" 意思不明，待修补
         */
        if (answer != null && !answer.equals("") && !answer.equals("\\N")) {
            return answer;
        } else {
            return "无答案";
        }
    }
}
