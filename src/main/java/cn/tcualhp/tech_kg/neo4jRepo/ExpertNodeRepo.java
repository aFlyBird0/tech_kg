package cn.tcualhp.tech_kg.neo4jRepo;

import cn.tcualhp.tech_kg.model.Neo4jNode.ExpertNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author lihepeng
 * @description expert的neo4j的repository类
 * @date 2019-11-01 00:21
 **/
public interface ExpertNodeRepo extends Neo4jRepository<ExpertNode, Long> {
    List<ExpertNode> getExpertNodesByName(@Param("name") String name);

    ExpertNode getExpertNodeByCode(@Param("code") String code);

    List<ExpertNode> getExpertNodesByCodeAndName(@Param("code") String code, @Param("name") String name);

    /**
     * 单纯演示cypher高级查询, 返回姓什么的专家
     *
     * @param lastname
     * @return java.util.List<cn.tcualhp.tech_kg.model.Neo4jNode.ExpertNode>
     * @author lihepeng
     * @description //TODO
     * @date 13:41 2019/11/4
     **/
    @Query("MATCH (n:Expert) where n.name =~'{0}李.*' RETURN n")
    List<ExpertNode> testCypher(@Param("lastname") String lastname);

    /**
     * 根据单位名称获取该单位有哪些专家
     * @param unitName
     * @return expertNode 的 list
     */
    @Query("match (n:Expert)-[r:expert_belong_to_unit]->(b:Unit) where b.name={unitName} return n limit 30")
    List<ExpertNode> getExpertNodesByUnitName(@Param("unitName") String unitName);

}
