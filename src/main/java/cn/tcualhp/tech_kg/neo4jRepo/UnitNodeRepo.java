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
     *
     * @param unitNode
     * @return
     */
    List<UnitNode> getUnitNodeByUnitNameContains(@Param("unitNode") String unitNode);

}
