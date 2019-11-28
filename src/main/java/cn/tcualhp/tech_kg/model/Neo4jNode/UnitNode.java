package cn.tcualhp.tech_kg.model.Neo4jNode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lihepeng
 * @description 单位节点
 * @date 2019-11-04 12:06
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@NodeEntity(label = "Unit")
public class UnitNode {
    @Id
    @GeneratedValue
    private Long nodeId;

    @Property(name = "name")
    private String unitName;

    /**
     * 属于入方向，即拥有
     */
    /**
     * 防止序列化循环
     */
    @JsonIgnore
    @Relationship(type = "expert_belong_to_unit", direction = Relationship.INCOMING)
    private Set<ExpertNode> expertNodes;

    public void addExpertNodes(ExpertNode expertNode) {
        if (expertNodes == null) {
            expertNodes = new HashSet<>();
        }
        expertNodes.add(expertNode);
    }

    @Override
    public String toString() {
        return "UnitNode{" +
                "nodeId=" + nodeId +
                ", name='" + unitName + '\'' +
                ", expertNodes=" + "DO NOT PRINT" +
                '}';
    }

}
