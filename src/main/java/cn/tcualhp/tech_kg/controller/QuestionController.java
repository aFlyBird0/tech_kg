package cn.tcualhp.tech_kg.controller;

import cn.tcualhp.tech_kg.common.Response;
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

    @PostMapping(value = "query")
    public Response query(@RequestBody Map<String, String> map){
        return new Response().success();
    }
}
