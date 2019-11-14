package cn.tcualhp.tech_kg.controller;

import cn.tcualhp.tech_kg.common.Response;
import cn.tcualhp.tech_kg.model.Neo4jNode.ExpertNode;
import cn.tcualhp.tech_kg.model.Neo4jNode.PaperNode;
import cn.tcualhp.tech_kg.neo4jRepo.ExpertNodeRepo;
import cn.tcualhp.tech_kg.neo4jRepo.PaperNodeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lihepeng
 * @description 论文查询
 * @date 2019-11-02 21:43
 **/
@RestController
@RequestMapping(value = "/paper")
public class PaperController {

    @Autowired
    private PaperNodeRepo paperNodeRepo;

    @Autowired
    private ExpertNodeRepo expertNodeRepo;

    /**
     * 通过论文名模糊查找论文
     * @param map
     * @return cn.tcualhp.tech_kg.common.Response
     * @author lihepeng
     * @description //TODO
     * @date 22:16 2019/11/2
     **/
    @PostMapping("/getPapersByPaperName")
    public Response getPapersByPaperName(@RequestBody Map<String, String> map){
        String name = map.get("name");
        if(StringUtils.isEmpty(name)){
            return new Response().failure(40003, "参数错误, 论文名不能为空");
        }
        List<PaperNode> paperNodes = paperNodeRepo.getPapersByNameContains(name);
        return new Response().success(paperNodes);
    }

    /**
     * 通过作者code查找论文，因为作者可能会重名，所以不用作者名字查找
     * @param map
     * @return
     * @author lihepeng
     * @description //TODO
     * @date 22:16 2019/11/2
     **/
    @PostMapping("/getPapersByExpertCode")
    public Response getPapersByExpertCode(@RequestBody Map<String, String> map){
        String code = map.get("code");
        if(StringUtils.isEmpty(code)){
            return new Response().failure(40004, "参数错误, 专家code不能为空");
        }
        ExpertNode expertNode = expertNodeRepo.getExpertNodeByCode(code);
        if (expertNode == null){
            return new Response().failure("无此专家");
        }
        Set<PaperNode> paperNodes = expertNode.getPaperNodes();
        return new Response().success(paperNodes);
    }

    /**
     * 通过论文 paper_id 查找论文
     * @param map
     * @return
     * @author dingjianhub
     * @description //TODO
     * @date 18:19 2019/11/14
     **/
    @PostMapping("/getPapersByPaperId")
    public Response getPapersByPaperId(@RequestBody Map<String, String> map){
        String paper_id = map.get("paper_id");
        if(StringUtils.isEmpty(paper_id)){
            return new Response().failure(40005, "参数错误, paper_id 不能为空");
        }
        List<PaperNode> paperNodes = paperNodeRepo.getPaperNodeByPaperId(paper_id);
        return new Response().success(paperNodes);
    }



}
