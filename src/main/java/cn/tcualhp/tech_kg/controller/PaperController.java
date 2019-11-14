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

import java.util.ArrayList;
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
     *
     * @param map
     * @return cn.tcualhp.tech_kg.common.Response
     * @author lihepeng
     * @description //TODO
     * @date 22:16 2019/11/2
     **/
    @PostMapping("/getPapersByPaperName")
    public Response getPapersByPaperName(@RequestBody Map<String, String> map) {
        String name = map.get("name");
        if (StringUtils.isEmpty(name)) {
            return new Response().failure(4001, "参数缺失");
        }
        List<PaperNode> paperNodes = paperNodeRepo.getPaperNodeByNameContains(name);
        return new Response().success(paperNodes);
    }

    /**
     * 通过 作者code 查找论文，因为作者可能会重名，所以不用作者名字查找
     *
     * @param map
     * @return
     * @author lihepeng
     * @description //TODO
     * @date 22:16 2019/11/2
     **/
    @PostMapping("/getPapersByExpertCode")
    public Response getPapersByExpertCode(@RequestBody Map<String, String> map) {
        String code = map.get("code");
        if (StringUtils.isEmpty(code)) {
            return new Response().failure(4001, "参数缺失");
        }
        ExpertNode expertNode = expertNodeRepo.getExpertNodeByCode(code);
        if (expertNode == null) {
            return new Response().failure("无此专家");
        }
        Set<PaperNode> paperNodes = expertNode.getPaperNodes();
        return new Response().success(paperNodes);
    }

    /**
     * 通过论文 paper_id 查询论文信息
     *
     * @param map
     * @return
     * @author dingjianhub
     * @description //TODO
     * @date 18:19 2019/11/14
     **/
    @PostMapping("/getPapersByPaperId")
    public Response getPapersByPaperId(@RequestBody Map<String, String> map) {
        String paperId = map.get("paper_id");
        if (StringUtils.isEmpty(paperId)) {
            return new Response().failure(4001, "参数缺失");
        }
        List<PaperNode> paperNodes = paperNodeRepo.getPaperNodeByPaperId(paperId);
        return new Response().success(paperNodes);
    }


    /**
     * 通过论文 keywords 查询论文信息
     *
     * @param map
     * @return
     */
    @PostMapping("/getPapersByKeywords")
    public Response getPapersByKeywords(@RequestBody Map<String, String> map) {
        String keywords = map.get("keywords");
        if (StringUtils.isEmpty(keywords)) {
            return new Response().failure(4001, "参数缺失");
        }
        List<PaperNode> paperNodes = paperNodeRepo.getPaperNodeByKeywordsContains(keywords);
        return new Response().success(paperNodes);
    }

    /**
     * 通过论文 year 来查询论文信息
     *
     * @param map
     * @return
     */
    @PostMapping("/getPapersByYear")
    public Response getPapersByYear(@RequestBody Map<String, String> map) {
        String year = map.get("year");
        if (StringUtils.isEmpty(year)) {
            return new Response().failure(4001, "参数缺失");
        }
        List<PaperNode> paperNodes = paperNodeRepo.getPaperNodeByYear(Integer.parseInt(year));
        return new Response().success(paperNodes);
    }


    /**
     * 通过论文发表 areaCode 来查询论文信息
     * @param map
     * @return
     */
    @PostMapping("/getPapersByAreaCode")
    public Response getPapersByAreaCode(@RequestBody Map<String, String> map) {
        String areaCode = map.get("areaCode");
        if (StringUtils.isEmpty(areaCode)) {
            return new Response().failure(4001, "参数缺失");
        }
        List<PaperNode> paperNodes = paperNodeRepo.getPaperNodeByAreaCode(Integer.parseInt(areaCode));
        return new Response().success(paperNodes);
    }

}
