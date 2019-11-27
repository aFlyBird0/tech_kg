package cn.tcualhp.tech_kg.process;

import java.io.IOException;
import java.util.List;

/**
 * @author jerry
 * @program tech_kg
 * @package_name cn.tcualhp.tech_kg.process
 * @description 问题加载器
 * @create 2019/11/27 10:49
 **/

public class QuestionLoader {
    private static Question question;

    public static void main(String[] args) throws IOException {
        QuestionLoader questionLoader = new QuestionLoader();
        List<Question> questions = question.loadQuestions("question.json");
        System.out.println(questions);
    }


}
