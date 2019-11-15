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

    /**
     * 论文名
     */
    @Property(name = "name")
    private String name;

    @Property(name = "paper_id")
    private String paperId;

    @Property(name = "keywords")
    private String keywords;

    @Property(name = "year")
    private int year;

    @Property(name = "abstract")
    private String paperAbstract;

    /**
     * 注意，此处的 area_code 是 int 类型。此处曾经引发一次惊心动魄的 trouble shot
     */
    @Property(name = "area_code")
    private int areaCode;

    @Property(name = "first_author_code")
    private String firstAuthorCode;

    @Property(name = "url")
    private String url;


    /**
     * 论文id
     */
    @Property(name = "paper_id")
    private String paperId;

    /**
     * 论文发表年份
     */
    @Property(name = "year")
    private Integer year;

    /**
     * 论文概要
     */
    @Property(name = "abstract")
    private String paperAbstract;

    @Property(name = "first_author_code")
    private String firstAuthorCode;

    /**
     * 发表入方向，即作者
     *
     * @param null
     * @return
     * @author lihepeng
     * @description //TODO
     * @date 14:53 2019/11/4
     **/
    //防止序列化循环
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

    @Override
    public String toString() {
        return "PaperNode{" +
                "nodeId=" + nodeId +
                ", name='" + name + '\'' +
                ", paperId='" + paperId + '\'' +
                ", year='" + year + '\'' +
                ", paperAbstract='" + "DO NOT PRINT" + '\'' +
                ", firstAuthorCode='" + "DO NOT PRINT" + '\'' +
                ", expertNodes=" + expertNodes +
                '}';
    }
}
