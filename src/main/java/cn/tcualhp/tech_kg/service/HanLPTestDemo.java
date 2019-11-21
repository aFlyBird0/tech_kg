package cn.tcualhp.tech_kg.service;

import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;

import java.util.List;

/**
 * @author jerry
 * @program tech_kg
 * @package_name cn.tcualhp.tech_kg.service
 * @description HanLP分词器测试Demo
 * @create 2019/11/20 18:34
 **/

public class HanLPTestDemo {
    private String string;


    public static void main(String[] args) {
        HanLPTestDemo demo = new HanLPTestDemo();
        demo.string = "在无线传感器网络中开发一个高效节能的路由算法需要充分利用有限的能量。针对现有异构无线传感器网络分簇路由算法未考虑节点距离基站的位置,以及在路由传输方面多是采用单跳路由机制,从而造成能量空洞等问题。文中将提出一种多级异构无线传感器网络高能效多跳分簇路由算法,该算法将节点位置与剩余能量作为考虑因素来选举簇头,增加距离基站近且剩余能量高的节点被选举为簇头的机率,同时采用多跳与单跳相结合的自适应路由通信机制,均衡全网能耗、提高全网能效。理论和仿真实验结果表明该算法在存活节点和网络吞吐量等性能方面优于现有算法。";

        List<Term> list = StandardTokenizer.segment(demo.string);
//        for (Term t : list) {
//            System.out.println(t);
//        }

//        System.out.println(TextRankKeyword.getKeywordList(demo.string, 5));

        list.clear();
        list = NLPTokenizer.segment("我新造一个词叫幻想乡你能识别并标注正确词性吗？");
        for (Term t : list) {
            System.out.println(t);
        }

    }

}


