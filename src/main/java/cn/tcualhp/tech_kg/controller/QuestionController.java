package cn.tcualhp.tech_kg.controller;

import cn.tcualhp.tech_kg.common.Response;
import cn.tcualhp.tech_kg.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author lihepeng
 * @description 智能问答
 * @date 2019-11-22 14:07
 **/
@RestController
@RequestMapping(value = "/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping(value = "/query")
    public Response query(@RequestBody Map<String, String> map){
        String question = map.get("question");
        String answer = questionService.magicAnswer(question);
        return new Response().success(answer);
    }
}
