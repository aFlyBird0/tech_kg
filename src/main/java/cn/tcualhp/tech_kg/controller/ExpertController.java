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

    @Autowired
    private PaperNodeRepo paperNodeRepo;

    /**
     * 通过专家名查询专家信息
     * @param map
     * @return  返回专家名匹配的专家的信息
     */
    @PostMapping("/getExpertByExpertName")
    public Response getExpertsByExpertName(@RequestBody Map<String, String> map) {
        String name = map.get("name");
        if (StringUtils.isEmpty(name)) {
            return new Response().failure(4001, "参数缺失");
        }
        System.out.println("传入姓名为：" + name);
        List<ExpertNode> expertNodes = expertNodeRepo.getExpertNodesByName(name);
        return new Response().success(expertNodes);
    }

    /**
     * 通过专家 code 专家号码，返回专家信息
     * @param map
     * @return  专家的节点。因为专家的 code 是唯一的，所以返回一个节点
     */
    @PostMapping("/getExpertByCode")
    public Response getExpertsByCode(@RequestBody Map<String, String> map) {
        String code = map.get("code");
        if (StringUtils.isEmpty(code)) {
            return new Response().failure(4002, "参数缺失");
        }
        ExpertNode expertNode = expertNodeRepo.getExpertNodeByCode(code);
        return new Response().success(expertNode);
    }

    /**
     * 通过论文 paperId 查询专家信息
     * @param map
     * @return 返回专家的信息
     */
    @PostMapping("/getExpertByPaperId")
    public Response getExpertsByPaperId(@RequestBody Map<String, String> map) {
        String paperId = map.get("paperId");
        if (StringUtils.isEmpty(paperId)) {
            return new Response().failure(4003, "参数缺失");
        }
        PaperNode paperNode = paperNodeRepo.getPaperNodeByPaperId(paperId);
        return new Response().success(paperNode.getExpertNodes());
    }

    @PostMapping("/getExpertByUnitName")
    public Response getExpertsByUnitName(@RequestBody Map<String, String> map) {
        String unitName = map.get("unitName");
        if (StringUtils.isEmpty(unitName)) {
            return new Response().failure(4003, "参数缺失");
        }
        List<ExpertNode> expertNodes = expertNodeRepo.getExpertNodesByUnitName(unitName);
        return new Response().success(expertNodes);
    }

}