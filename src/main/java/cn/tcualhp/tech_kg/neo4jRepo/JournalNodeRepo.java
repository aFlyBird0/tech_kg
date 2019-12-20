package cn.tcualhp.tech_kg.neo4jRepo;

import cn.tcualhp.tech_kg.model.Neo4jNode.JournalNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 该 node 在 neo4j 的名称，对于 期刊 节点，其名称为 Journal
 */
//@NodeEntity(label = "Journal")    // 注意此处不应该出现改语句，此语句引发了一次长达 4 小时的 trouble shot
/**
 * @author jerry
 * @description 期刊 Journal 的Repo
 * @date 2019-11-15 11:25
 **/
public interface JournalNodeRepo extends Neo4jRepository<JournalNode, Long> {
    /**
     * 根据期刊的 journal_name 获取期刊信息
     *
     * @param journalName 期刊名
     * @return 返回论文节点 PaperNode 的 list
     */
    List<JournalNode> getJournalNodeByJournalNameContains(@Param("journalName") String journalName);

    /**
     * 根据期刊的期刊质量级 journalQuality 来查询期刊信息
     * @param journalQuality
     * @return
     */
    List<JournalNode> getJournalNodeByJournalQuality(@Param("journalQuality") String journalQuality);
}
