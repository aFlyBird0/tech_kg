package cn.tcualhp.tech_kg.neo4jRepo;

import cn.tcualhp.tech_kg.model.Neo4jNode.PaperNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author lihepeng
 * @description 论文查询neo4j的Repo
 * @date 2019-11-02 21:44
 **/
public interface PaperNodeRepo extends Neo4jRepository<PaperNode, Long> {
    List<PaperNode> getPapersByNameContains(@Param("name") String name);
    PaperNode getPaperNodeByPaperId(@Param("paperId") String paperId);
}
