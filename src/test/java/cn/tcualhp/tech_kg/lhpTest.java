package cn.tcualhp.tech_kg;

import cn.tcualhp.tech_kg.model.Neo4jNode.ExpertNode;
import cn.tcualhp.tech_kg.neo4jRepo.ExpertNodeRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author lihepeng
 * @description lhp的测试类
 * @date 2019-11-04 14:25
 **/
public class lhpTest extends TechKgApplicationTests{

    @Autowired
    private ExpertNodeRepo expertNodeRepo;

    @Test
    void testCypher(){
        List<ExpertNode> expertNodes = expertNodeRepo.testCypher("李");
        for (int i=0; i<expertNodes.size(); i++){
            System.out.println(expertNodes.get(i));
        }
    }
}
