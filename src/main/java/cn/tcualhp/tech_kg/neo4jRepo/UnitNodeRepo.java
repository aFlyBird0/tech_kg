package cn.tcualhp.tech_kg.neo4jRepo;

import cn.tcualhp.tech_kg.model.Neo4jNode.UnitNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 单位/机构 Node 的 repo
 */
public interface UnitNodeRepo extends Neo4jRepository<UnitNode, Long> {

    /**
     *  通过 unitName 查询 UnitNode 的信息
     * @param unitName 单位名称
     * @return  list 单位的 unitNode
     */
    List<UnitNode> getUnitNodeByUnitNameContains(@Param("unitName") String unitName);

}
