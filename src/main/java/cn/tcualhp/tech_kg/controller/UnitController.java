package cn.tcualhp.tech_kg.controller;

import cn.tcualhp.tech_kg.common.Response;
import cn.tcualhp.tech_kg.model.Neo4jNode.UnitNode;
import cn.tcualhp.tech_kg.neo4jRepo.UnitNodeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author jerry
 * @program tech_kg
 * @package_name cn.tcualhp.tech_kg.controller
 * @description 通过单位查询相关信息
 * @create 2019/11/28 14:54
 **/
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/unit")
public class UnitController {
    @Autowired
    private UnitNodeRepo unitNodeRepo;

    /**
     * 通过 单位/机构名 返回 UnitNode
     * @param map
     * @return 返回 UnitNode
     */
    @PostMapping("/getUnitNodesByUnitName")
    public Response getUnitNodesByUnitName(@RequestBody Map<String, String> map) {
        String unitName = map.get("unitName");
        if (StringUtils.isEmpty(unitName)) {
            return new Response().failure(4001, "参数缺失");
        }
        List<UnitNode> unitNodes = unitNodeRepo.getUnitNodeByUnitNameContains(unitName);
        return new Response().success(unitNodes);
    }
}
