package cn.tcualhp.tech_kg.model.Neo4jNode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

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
public class UnitNode {
    @Id
    @GeneratedValue
    private Long nodeId;

    @Property(name = "name")
    private String name;

    /**
     * 属于入方向，即拥有
     */
    @JsonIgnore
    @Relationship(type = "expert_belong_to_unit", direction = Relationship.INCOMING)
    private Set<ExpertNode> expertNodes;

    public void addExpertNodes(ExpertNode expertNode){
        if (expertNodes == null){
            expertNodes = new HashSet<>();
        }
        expertNodes.add(expertNode);
    }
}
