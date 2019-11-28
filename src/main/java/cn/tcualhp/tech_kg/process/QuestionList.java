package cn.tcualhp.tech_kg.process;

import cn.tcualhp.tech_kg.utils.LoadJsonUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;

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
@AllArgsConstructor
public class QuestionList {
    private String fileName;
    private int questionType;
    private String questionDescription;
    private List<Question> questions;

    public QuestionList(String fileName) {
        this.fileName = fileName;

        if (this.fileName == null) {
            return;
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = LoadJsonUtil.getJsonObject(this.fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return;
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

        //获取问题描述
        String questionDescription = (String) jsonObject.get("questionDescription");
        this.questionDescription = questionDescription;

        /**
         * 获取问题类型
         * 之前漏了一行导致分类器所有问题类型都是默认0
         * 导致分类结果都是类型 0
         * 还好一下子就找到了
         *
         */
        this.questionType = Integer.parseInt(jsonObject.get("questionType").toString());

    }

    public static void main(String[] args) throws IOException {
        QuestionList questionList = new QuestionList("questions/00questionPublish.json");
        for (Question q : questionList.questions) {
            System.out.println(q);
        }
    }

}
