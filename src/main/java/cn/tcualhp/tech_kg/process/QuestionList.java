package cn.tcualhp.tech_kg.process;

import cn.tcualhp.tech_kg.utils.LoadJsonUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.List;

/**
 * @author jerry
 * @program tech_kg
 * @package_name cn.tcualhp.tech_kg.process
 * @description 问题类，定义问题的相关成员信息等
 * @create 2019/11/27 11:24
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionList {
    private String fileName;
    private int questionType;
    private String questionDescription;
    private List<Question> questions;

    public QuestionList(String fileName) {
        this.fileName = fileName;
    }

    public List<Question> getQuestions() {
        if (this.fileName == null) {
            return null;
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = LoadJsonUtil.getJsonObject(this.fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        /**
         *  获取问题数组
         */
        JSONArray jsonArray = (JSONArray) jsonObject.get("questions");

        /**
         *  转换为问题列表
         */
        List<Question> questions = JSONArray.parseArray(jsonArray.toString(), Question.class);
        this.questions = questions;
        return questions;
    }

    public static void main(String[] args) throws IOException {
        QuestionList questionList = new QuestionList("questions/questionPublish.json");
        for (Question q : questionList.getQuestions()) {
            System.out.println(q);
        }
    }

}
