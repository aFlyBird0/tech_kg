package cn.tcualhp.tech_kg;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class TechKgApplicationTests {

    @Test
    void contextLoads() {
    }

    @Before
    public void init(){
        System.out.println("开始测试");
    }

    @After
    public void end(){
        System.out.println("结束测试");
    }

}
