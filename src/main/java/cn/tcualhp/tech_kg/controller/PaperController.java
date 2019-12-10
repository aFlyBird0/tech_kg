package cn.tcualhp.tech_kg.controller;

import cn.tcualhp.tech_kg.common.Response;
import cn.tcualhp.tech_kg.model.Neo4jNode.ExpertNode;
import cn.tcualhp.tech_kg.model.Neo4jNode.PaperNode;
import cn.tcualhp.tech_kg.neo4jRepo.ExpertNodeRepo;
import cn.tcualhp.tech_kg.neo4jRepo.PaperNodeRepo;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author lihepeng
 * @description 论文查询
 * @date 2019-11-02 21:43
 **/
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/paper")
public class PaperController {

    @Autowired
    private PaperNodeRepo paperNodeRepo;

    @Autowired
    private ExpertNodeRepo expertNodeRepo;

    /**
     * 通过论文名模糊查找论文
     *
     * @param map
     * @return cn.tcualhp.tech_kg.common.Response
     * @author lihepeng
     * @description 通过论文名查找论文
     * @date 22:16 2019/11/2
     **/
    @PostMapping("/getPapersByPaperName")
    public Response getPapersByPaperName(@RequestBody Map<String, String> map) {
        String name = map.get("name");
        if (StringUtils.isEmpty(name)) {
            return new Response().failure(4001, "参数缺失");
        }
        List<PaperNode> paperNodes = paperNodeRepo.getPaperNodeByNameContains(name);
        return new Response().success(paperNodes);
    }

    /**
     * 通过 作者code 查找论文，因为作者可能会重名，所以不用作者名字查找
     *
     * @param map
     * @return
     * @author lihepeng
     * @description //TODO
     * @date 22:16 2019/11/2
     **/
    @PostMapping("/getPapersByExpertCode")
    public Response getPapersByExpertCode(@RequestBody Map<String, String> map) {
        String code = map.get("code");
        if (StringUtils.isEmpty(code)) {
            return new Response().failure(4001, "参数缺失");
        }
        ExpertNode expertNode = expertNodeRepo.getExpertNodeByCode(code);
        if (expertNode == null) {
            return new Response().failure("无此专家");
        }
        Set<PaperNode> paperNodes = expertNode.getPaperNodes();
        return new Response().success(paperNodes);
    }

    /**
     * 通过论文 paper_id 查询论文信息
     *
     * @param map
     * @return
     * @author dingjianhub
     * @description //TODO
     * @date 18:19 2019/11/14
     **/
    @PostMapping("/getPapersByPaperId")
    public Response getPapersByPaperId(@RequestBody Map<String, String> map) {
        String paperId = map.get("paperId");
        if (StringUtils.isEmpty(paperId)) {
            return new Response().failure(4001, "参数缺失");
        }
        PaperNode paperNode = paperNodeRepo.getPaperNodeByPaperId(paperId);
        return new Response().success(paperNode);
    }


    /**
     * 通过论文 keywords 查询论文信息
     *
     * @param map
     * @return
     */
    @PostMapping("/getPapersByKeywords")
    public Response getPapersByKeywords(@RequestBody Map<String, String> map) {
        String keywords = map.get("keywords");
        if (StringUtils.isEmpty(keywords)) {
            return new Response().failure(4001, "参数缺失");
        }
        List<PaperNode> paperNodes = paperNodeRepo.getPaperNodeByKeywordsContains(keywords);
        return new Response().success(paperNodes);
    }

    /**
     * 通过论文 year 来查询论文信息
     *
     * @param map
     * @return
     */
    @PostMapping("/getPapersByYear")
    public Response getPapersByYear(@RequestBody Map<String, String> map) {
        String year = map.get("year");
        if (StringUtils.isEmpty(year)) {
            return new Response().failure(4001, "参数缺失");
        }
        List<PaperNode> paperNodes = paperNodeRepo.getPaperNodeByYear(Integer.parseInt(year));
        return new Response().success(paperNodes);
    }


    /**
     * 通过论文发表 areaCode 来查询论文信息
     *
     * @param map
     * @return
     */
    @PostMapping("/getPapersByAreaCode")
    public Response getPapersByAreaCode(@RequestBody Map<String, String> map) {
        String areaCode = map.get("areaCode");
        if (StringUtils.isEmpty(areaCode)) {
            return new Response().failure(4001, "参数缺失");
        }
        List<PaperNode> paperNodes = paperNodeRepo.getPaperNodeByAreaCode(Integer.parseInt(areaCode));
        return new Response().success(paperNodes);
    }

    /**
     * 通过论文的 abstract 摘要来查询论文信息
     *
     * @param map
     * @return 返回 paperNode 的 list
     */
    @PostMapping("/getPapersByAbstract")
    public Response getPapersByAbstract(@RequestBody Map<String, String> map) {
        String summary = map.get("abstract");
        if (StringUtils.isEmpty(summary)) {
            return new Response().failure(4001, "参数缺失");
        }
        List<PaperNode> paperNodes = paperNodeRepo.getPaperNodeBySummaryContains(summary);
        return new Response().success(paperNodes);
    }

    /**
     * 通过论文的 abstract 摘要来查询论文信息
     *
     * @param map
     * @return 返回 paperNode 的 list
     */
    @PostMapping("/getPapersByAbstractNLP")
    public Response getPapersByAbstractNLP(@RequestBody Map<String, String> map) {
        String summary = map.get("abstract");
        if (StringUtils.isEmpty(summary)) {
            return new Response().failure(4002, "参数缺失");
        }
        List<Term> summaryList = StandardTokenizer.segment(summary);
        List<PaperNode> allPaperNodes = new ArrayList<>();
        List<PaperNode> paperNodes;

        /**
         * 对于分词出来的每一个词，在 keywords 字段中查询相匹配的 paperNode
         */
        for (Term term : summaryList) {
            paperNodes = paperNodeRepo.getPaperNodeByKeywordsContains(term.word);
            while (!paperNodes.isEmpty()) {
                PaperNode paperNode = paperNodes.get(0);
                try {
                    if (!allPaperNodes.contains(paperNode)) {
                        allPaperNodes.add(paperNode);
                    }
                } catch (NullPointerException e) {
                    System.out.println("空指针错误");
                }
                paperNodes.remove(0);

            }
        }
        return new Response().success(allPaperNodes);
    }


    /**
     * 通过专家姓名返回论文的信息
     * @param map
     * @return
     */
    @PostMapping("/getPapersByExpertName")
    public Response getPapersByExpertName(@RequestBody Map<String, String> map) {
        String expertName = map.get("expertName");
        if (StringUtils.isEmpty(expertName)) {
            return new Response().failure(4001, "参数缺失");
        }
//        StringBuffer finalExpertName = new StringBuffer();
//        finalExpertName.append(".*");
//        finalExpertName.append(expertName);
//        finalExpertName.append(".*");

        List<PaperNode> paperNodes = paperNodeRepo.getPaperNodesByExpertName(expertName);
        return new Response().success(paperNodes);
    }


    /**
     * 通过 专家 1 姓名 和 专家 2 姓名，查询论文
     *
     * @param map
     * @return paperNode 的 list
     */
    @PostMapping("/getPapersByExpertCooperationRelationship")
    public Response getPapersByExpertCooperationRelationship(@RequestBody Map<String, List<String>> map) {
        List<String> experts = map.get("expertsNames");
//        System.out.println(experts.get(0) + " : " + experts.get(1));
        if (StringUtils.isEmpty(experts)) {
            return new Response().failure(4001, "参数缺失");
        }
        List<PaperNode> paperNodes = paperNodeRepo.getPaperNodeByCooperation(experts.get(0), experts.get(1));
        return new Response().success(paperNodes);
    }

    /**
     * 通过单位名称获取某单位拥有哪些论文/专利
     * 支持模糊查询。需要将 unitName 的参数改写为 .*unitName.*
     * @param map
     * @return paperNode 的 list
     */
    @PostMapping("/getPapersByUnitHavePaper")
    public Response getPapersByUnitHavePaper(@RequestBody Map<String, String> map) {
        String unitName = map.get("unitName");
        if (StringUtils.isEmpty(unitName)) {
            return new Response().failure(4001, "参数缺失");
        }
        StringBuffer finalUnitName = new StringBuffer();
        finalUnitName.append(".*");
        finalUnitName.append(unitName);
        finalUnitName.append(".*");

        List<PaperNode> paperNodes = paperNodeRepo.getPaperNodeByUnitHavePaper(finalUnitName.toString());
        return new Response().success(paperNodes);
    }

    /**
     * 通过专家姓名和论文关键字查询论文信息。
     * 支持模糊查询。
     * 下面演示了模糊查询使用例子。
     *
     * @param map
     * @return paperNodes 的 list
     */
    @PostMapping("/getPapersByExpertNameAndKeywords")
    public Response getPapersByExpertNameAndKeywords(@RequestBody Map<String, String> map) {
        String keywords = map.get("keywords");
        String expertName = map.get("expertName");

        if (StringUtils.isEmpty(expertName)) {
            return new Response().failure(4001, "参数缺失");
        }
        if (StringUtils.isEmpty(keywords)) {
            return new Response().failure(4001, "参数缺失");
        }

        StringBuffer finalExpertName = new StringBuffer();
        StringBuffer finalKeywords = new StringBuffer();

        finalExpertName.append(".*");
        finalExpertName.append(expertName);
        finalExpertName.append(".*");

        finalKeywords.append(".*");
        finalKeywords.append(keywords);
        finalKeywords.append(".*");

        List<PaperNode> paperNodes = paperNodeRepo.getPaperNodeByExpertNameAndKeywords(finalKeywords.toString(), finalExpertName.toString());
        return new Response().success(paperNodes);
    }

}
