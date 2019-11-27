package cn.tcualhp.tech_kg.process;

import cn.tcualhp.tech_kg.utils.LoadJsonUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jerry
 * @program tech_kg
 * @package_name cn.tcualhp.tech_kg.process
 * @description 问题类，定义问题的相关成员信息等
 * @create 2019/11/27 11:24
 **/

public class Question {
    private String questionType;
    private long id;
    private String value;

    /**
     * 返回指定问题 json 文件下，指定的问题类型下的 问题的 list
     * @param filename 文件名
     * @param questionType 问题类型
     * @return list 问题类型列表
     * @throws IOException
     */
    public List<Question> getQuestionsList(String filename, String questionType) throws IOException {
        JSONObject jsonObject = LoadJsonUtil.getJsonObject(filename);
        List<Question> questionsList = new ArrayList<>();
        JSONArray questions = jsonObject.getJSONArray(questionType);
        for (int i = 0; i < questions.size();i++) {
            Question q = new Question();
            q.id = i + 1;
            q.value = questions.getJSONObject(i).getString("value");
            questionsList.add(q);
        }
        return questionsList;
    }

    public static void main(String[] args) throws IOException {
        Question question = new Question();
        List<Question> questions = question.getQuestionsList("questions/questionPublish.json","publish");
        for (Question q:questions) {
            System.out.println(q.id + q.value);
        }
        List<Question> questions1 = question.getQuestionsList("questions/questionWorkIn.json","workIn");
        for (Question q:questions1) {
            System.out.println(q.id + q.value);
        }
    }


}
