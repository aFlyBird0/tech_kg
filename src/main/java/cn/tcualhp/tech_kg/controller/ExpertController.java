package cn.tcualhp.tech_kg.controller;

import cn.tcualhp.tech_kg.common.Response;
import cn.tcualhp.tech_kg.model.Neo4jNode.ExpertNode;
import cn.tcualhp.tech_kg.neo4jRepo.ExpertNodeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author lihepeng
 * @description 查询专家
 * @date 2019-10-31 21:27
 **/
@RestController
@RequestMapping(value = "/expert")
public class ExpertController {

    @Autowired
    private ExpertNodeRepo expertNodeRepo;

    @PostMapping("/getExpertsByName")
    public Response getExpertsByName(@RequestBody Map<String, String> map){
        String name = map.get("name");
        if(StringUtils.isEmpty(name)){
            return new Response().failure(40001, "参数错误，name不能为空");
        }
        System.out.println("传入姓名为：" + name);
        List<ExpertNode> expertNodes = expertNodeRepo.getExpertNodesByName(name);
        return new Response().success(expertNodes);
    }

    @PostMapping("/getExpertByCode")
    public Response getExpertsByCode(@RequestBody Map<String, String> map){
        String code = map.get("code");
        if(code.isEmpty()){
            return new Response().failure(40002, "参数错误，code不能为空");
        }
        ExpertNode expertNode = expertNodeRepo.getExpertNodeByCode(code);
        return new Response().success(expertNode);
    }
}
