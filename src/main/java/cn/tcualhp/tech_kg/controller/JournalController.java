package cn.tcualhp.tech_kg.controller;

import cn.tcualhp.tech_kg.common.Response;
import cn.tcualhp.tech_kg.model.Neo4jNode.JournalNode;
import cn.tcualhp.tech_kg.neo4jRepo.JournalNodeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author jerry
 * @program tech_kg
 * @package_name cn.tcualhp.tech_kg.controller
 * @description 期刊 controller
 * @create 2019/11/15 11:13
 **/

@RestController
@RequestMapping(value = "/journal")
public class JournalController {
    @Autowired
    private JournalNodeRepo journalNodeRepo;

    /**
     * 通过期刊名称 journalName 查询期刊信息
     *
     * @param map
     * @return 包含查询关键字的期刊
     */
    @PostMapping("/getJournalNodeByJournalName")
    public Response getJournalNodeByJournalName(@RequestBody Map<String, String> map) {
        String journalName = map.get("journalName");
        if (StringUtils.isEmpty(journalName)) {
            return new Response().failure(4001, "参数缺失");
        }
        List<JournalNode> journalNodes = journalNodeRepo.getJournalNodeByJournalNameContains(journalName);
        return new Response().success(journalNodes);
    }


    /**
     * 通过期刊的质量等级 journalQuality 查询期刊的信息
     * @param map
     * @return  质量等级符合查询关键字的期刊
     */
    @PostMapping("/getJournalNodeByJournalQuality")
    public Response getJournalNodeByJournalQuality(@RequestBody Map<String, String> map) {
        String journalQuality = map.get("journalQuality");
        if (StringUtils.isEmpty(journalQuality)) {
            return new Response().failure(4001, "参数缺失");
        }
        List<JournalNode> journalNodes = journalNodeRepo.getJournalNodeByJournalQuality(journalQuality);
        return new Response().success(journalNodes);
    }


}
