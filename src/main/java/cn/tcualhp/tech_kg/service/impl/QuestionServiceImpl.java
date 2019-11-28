package cn.tcualhp.tech_kg.service.impl;

import cn.tcualhp.tech_kg.model.Neo4jNode.ExpertNode;
import cn.tcualhp.tech_kg.model.Neo4jNode.PaperNode;
import cn.tcualhp.tech_kg.model.Neo4jNode.UnitNode;
import cn.tcualhp.tech_kg.neo4jRepo.ExpertNodeRepo;
import cn.tcualhp.tech_kg.neo4jRepo.PaperNodeRepo;
import cn.tcualhp.tech_kg.service.Question2ModelString;
import cn.tcualhp.tech_kg.service.QuestionService;
import org.apache.spark.sql.catalyst.expressions.Exp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    @Override
    public String magicAnswer(String question) {
        ArrayList<String> reStrings = null;
        try {
             reStrings = question2ModelString.analyQuery(question);
        }
        catch (Exception e){
            e.printStackTrace();
            return "服务器发生错误";
        }
        int modelIndex = Integer.parseInt(reStrings.get(0));
        String answer = null;
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
         * 匹配问题模板
         */
        switch (modelIndex) {
            case 0:
                /**
                 * 抽象问题模板：nr 论文
                 */
                expertName = reStrings.get(1);
                /**
                 * 注意这里要判断重名
                 * 现在只是简单取第一个
                 * 以后要加如下判断
                 * 如果人多余一个要询问是哪个
                 * 如果只有一个直接返回论文
                 */
                expertNodes= expertNodeRepo.getExpertNodesByName(expertName);
                if (expertNodes.size()<1){
                    answer = "无此人";
                    break;
                }
                else {
                    expertNode = expertNodes.get(0);
                }
                paperNodes = expertNode.getPaperNodes();
                /**
                 * 这里先简单处理一下
                 */
                answer = paperNodes.toString();
                break;
            case 1:
                /**
                 * 抽象问题模板：nr 单位
                 */
                expertName = reStrings.get(1);
                /**
                 * 注意这里要判断重名
                 * 现在只是简单取第一个
                 * 以后要加如下判断
                 * 如果人多余一个要询问是哪个
                 * 如果只有一个直接返回论文
                 */
                expertNode = expertNodeRepo.getExpertNodesByName(expertName).get(0);
                unitNodes = expertNode.getUnitNodes();
                answer = unitNodes.toString();
            case 2:
                /**
                 * 抽象模板详见classify/classifications.json
                 * 这里最好把case都封装成函数，现在太乱了
                 * 比如所有的case的变量不能重复定义，很坑
                 * 这里是 2 nr n2r 合作论文
                 */
                expertName = reStrings.get(1);
                expertName2 = reStrings.get(2);
                answer = "service待写";
            case 3:
                answer = "待写";
            case 4:
                answer = "待写";
            case 5:
                answer = "待写";
            case 7:
                answer = "待写";
            default:
                break;
        }

        System.out.println(answer);
        if (answer != null && !answer.equals("") && !answer.equals("\\N")) {
            return answer;
        } else {
            return "无答案";
        }
    }

    public static void main(String[] args) {
        QuestionServiceImpl questionService = new QuestionServiceImpl();
        String answer = questionService.magicAnswer("张阳发表了什么论文");
    }
}
