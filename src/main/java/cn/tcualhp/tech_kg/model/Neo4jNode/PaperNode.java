package cn.tcualhp.tech_kg.model.Neo4jNode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author lihepeng
 * @description 论文实体类
 * @date 2019-11-02 21:40
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@NodeEntity(label = "Paper")
public class PaperNode {
    @Id
    @GeneratedValue
    private long nodeId;

    @Property(name = "name")
    private String name;

    @Property(name = "paper_id")
    private String paperId;

    @Property(name = "keywords")
    private String keywords;

    @Property(name = "year")
    private String year;

    /**
     * 发表入方向，即作者
     *
     * @param null
     * @return
     * @author lihepeng
     * @description //TODO
     * @date 14:53 2019/11/4
     **/
    @JsonIgnore
    @Relationship(type = "write", direction = Relationship.INCOMING)
    private Set<ExpertNode> expertNodes;

    public void addExpertNodes(ExpertNode expertNode) {
        // 如果专家节点为空
        if (expertNodes == null) {
            expertNodes = new HashSet<>();
        }
        expertNodes.add(expertNode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaperNode paperNode = (PaperNode) o;
        return nodeId == paperNode.nodeId &&
                name.equals(paperNode.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeId, name);
    }
}
