package cn.tcualhp.tech_kg.process;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

/**
 * @author jerry
 * @program tech_kg
 * @package_name cn.tcualhp.tech_kg.process
 * @description 问题类，定义问题的相关成员信息等
 * @create 2019/11/27 11:24
 **/

public class Question {
    private String id;
    private String question;

    public  List<Question> loadQuestions(String filename) throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream(filename);
        String text = IOUtils.toString(inputStream, "utf8");
        List<Question> questionList = JSON.parseArray(text, Question.class);
        return questionList;
    }

    public static void main(String[] args) throws IOException {
        Question question = new Question();
        List<Question> questions = question.loadQuestions("question.json");
//        for (question:questions) throw NullPointerException{
//            System.out.println(question);
//        }

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Question question1 = (Question) o;
        return id.equals(question1.id) &&
                Objects.equals(question, question1.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", question='" + question + '\'' +
                '}';
    }
}
