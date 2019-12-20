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
     * 支持模糊查询。但是需要用户在调用时，自行修改参数 unitName 的值
     * 例如模糊查询 “ 杭州电子 ” ,需要用户对参数进行修改，修改为 “  .*杭州电子.*  ” ，即可完成模糊查询。
     * 使用样例见  ExpertController.java 中 “ public Response getExpertsByUnitName() ”
     * @param unitName
     * @return expertNode 的 list ， 最多返回 30 个结果
     */
    @Query("match (n:Expert)-[r:expert_belong_to_unit]->(b:Unit) where b.name=~{unitName} return n limit 30")
    List<ExpertNode> getExpertNodesByUnitNameContains(@Param("unitName") String unitName);

    /**
     * 根据专家发表的论文是否涉足某个领域，查找专家信息
     * 支持模糊查询。
     * 查询参数设置同 ExpertNodeRepo.java 中的 List<ExpertNode> getExpertNodesByUnitNameContains()
     * @param unitName
     * @return 专家的信息的 list ，最多返回 30 个结果
     */
    @Query("match (n:Expert)-[r:write]->(p:Paper) where p.subject_code=~{areaCode} return n limit 30")
    List<ExpertNode> getExpertNodesByAreaCodeContains(@Param("areaCode") String areaCode);

}
