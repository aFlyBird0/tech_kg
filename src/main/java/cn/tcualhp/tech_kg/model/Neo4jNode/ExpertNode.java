package cn.tcualhp.tech_kg.model.Neo4jNode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lihepeng
 * @description 专家节点
 * @date 2019-11-01 00:14
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@NodeEntity(label = "Expert")
public class ExpertNode {

    @Id
    @GeneratedValue
    private long nodeId;

    /**
     * 专家姓名
     * */
    @Property(name = "name")
    private String name;

    /**
     * 专家编号，编号唯一
     * */
    @Property(name = "code")
    private String code;

    /**
     * 发表出方向
     */
    @Relationship(type = "write", direction = Relationship.OUTGOING)
    private Set<PaperNode> paperNodes;

    /**
     * 属于出方向
     */
    @Relationship(type = "expert_belong_to_unit", direction = Relationship.OUTGOING)
    private Set<UnitNode> unitNodes;

    public void addUnitNodes(UnitNode unitNode){
        if (unitNodes == null){
            unitNodes = new HashSet<>();
        }
        unitNodes.add(unitNode);
    }

    public void addPaperNode(PaperNode paperNode){
        if (paperNodes == null){
            paperNodes = new HashSet<PaperNode>();
        }
        paperNodes.add(paperNode);
    }

    public Set<PaperNode> getPaperNodes() {
        return paperNodes;
    }
}
