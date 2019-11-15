package cn.tcualhp.tech_kg;

import cn.tcualhp.tech_kg.model.Neo4jNode.ExpertNode;
import cn.tcualhp.tech_kg.model.Neo4jNode.PaperNode;
import cn.tcualhp.tech_kg.neo4jRepo.ExpertNodeRepo;
import cn.tcualhp.tech_kg.neo4jRepo.PaperNodeRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

/**
 * @author lihepeng
 * @description lhp的测试类
 * @date 2019-11-04 14:25
 **/
public class lhpTest extends TechKgApplicationTests{

    @Autowired
    private ExpertNodeRepo expertNodeRepo;

    @Autowired
    private PaperNodeRepo paperNodeRepo;

    @Test
    void testCypher(){
        List<ExpertNode> expertNodes = expertNodeRepo.testCypher("李");
        for (ExpertNode expertNode: expertNodes
             ) {
            System.out.println(expertNode);
        }
    }

    @Test
    void getPapersByPaperName(){
        List<PaperNode> paperNodes = paperNodeRepo.getPapersByNameContains("基于");
        for (PaperNode paperNode: paperNodes
             ) {
            System.out.println(paperNode);
        }
        Set<ExpertNode> expertNodes = paperNodes.get(0).getExpertNodes();
        for (ExpertNode expertNode: expertNodes
             ) {
            System.out.println(expertNode);
        }
    }
}
