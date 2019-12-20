package cn.tcualhp.tech_kg.model.Neo4jNode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import java.util.Objects;

/**
 * @author jerry
 * @program tech_kg
 * @package_name cn.tcualhp.tech_kg.model.Neo4jNode
 * @description 期刊节点
 * @create 2019/11/15 11:07
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@NodeEntity(label = "Journal")
public class JournalNode {
    @Id
    @GeneratedValue
    private long nodeId;

    @Property(name = "name")
    private String name;

    @Property(name = "journal_name")
    private String journalName;

    @Property(name = "journal_quality")
    private String journalQuality;

    @Property(name = "journal_url")
    private String journalUrl;


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        JournalNode journalNode = (JournalNode) obj;
        return nodeId == journalNode.nodeId &&
                journalName.equals(journalNode.journalName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeId, journalName);
    }

    @Override
    public String toString() {
        return "JournalNode{" +
                "nodeId=" + nodeId +
                ", journalName='" + journalName + '\'' +
                ", journalQuality='" + journalQuality + '\'' +
                ", journalUrl='" + journalUrl + '\'' +
                '}';
    }
}
