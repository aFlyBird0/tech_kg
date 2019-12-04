package cn.tcualhp.tech_kg.neo4jRepo;

import cn.tcualhp.tech_kg.model.Neo4jNode.ExpertNode;
import cn.tcualhp.tech_kg.model.Neo4jNode.PaperNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author lihepeng
 * @description 论文查询neo4j的Repo
 * @date 2019-11-02 21:44
 **/
public interface PaperNodeRepo extends Neo4jRepository<PaperNode, Long> {
    /**
     * 根据论文 name 论文名 获取论文信息
     *
     * @param name 论文名
     * @return 返回论文节点 PaperNode 的 list
     */
    List<PaperNode> getPaperNodeByNameContains(@Param("name") String name);

    /**
     * 根据论文 paperId 来获取论文信息
     *
     * @param paperId 论文 paperId
     * @return 返回论文节点 PaperNode 的 list
     */
    PaperNode getPaperNodeByPaperId(@Param("paperId") String paperId);

    /**
     * 根据论文关键词获取论文信息
     *
     * @param keywords 论文关键词
     * @return 返回论文节点 PaperNode 的 list
     */
    List<PaperNode> getPaperNodeByKeywordsContains(@Param("keywords") String keywords);

    /**
     * 根据论文发表 year 年份获取论文信息
     *
     * @param year 论文发表年
     * @return 返回论文节点 PaperNode 的 list
     */
    List<PaperNode> getPaperNodeByYear(@Param("year") int year);

    /**
     * 通过论文发表的 area_code 获取论文信息
     *
     * @param areaCode 论文发表分区号
     * @return 返回论文节点 PaperNode 的 list
     */
    List<PaperNode> getPaperNodeByAreaCode(@Param("areaCode") int areaCode);

    /**
     * 通过论文发表的  abstract 摘要，来获取论文信息
     *
     * @param summary
     * @return 返回论文节点 PaperNode 的 list
     */
    List<PaperNode> getPaperNodeBySummaryContains(@Param("paperAbstract") String summary);

    /**
     * 通过论文发表的  abstract 摘要，来获取论文信息
     *
     * @param summary
     * @return 返回论文节点 PaperNode 的 list
     */
    PaperNode getPaperNodeNLPBySummaryContains(@Param("paperAbstract") String summary);

    /**
     * 通过论文的 作者姓名，获取论文的信息
     *
     * @param firstAuthor 论文作者
     * @return
     */
    List<PaperNode> getPaperNodeByFirstAuthor(@Param("firstAuthor") String firstAuthor);

    List<PaperNode> getPaperNodeBySecondAuthor(@Param("secondAuthor") String secondAuthor);

    List<PaperNode> getPaperNodeByThirdAuthor(@Param("thirdAuthor") String thirdAuthor);

    List<PaperNode> getPaperNodeByFourthAuthor(@Param("fourthAuthor") String fourthAuthor);

    /**
     * 通过 专家 1 姓名 和 专家 2 姓名，查询论文
     *
     * @param expertName1 专家姓名 1
     * @param expertName2 专家姓名 2
     * @return
     */
    @Query("match(n:Expert)-[r:write]->(b:Paper)<-[t:write]-(p:Expert) where n.name={expertName1} and p.name={expertName2} return b  limit 30")
    List<PaperNode> getPaperNodeByCooperation(@Param("expertName1") String expertName1, @Param("expertName2") String expertName2);

    /**
     * 通过单位名称获取某单位拥有哪些论文/专利
     *
     * @param unitName 单位名称
     * @return
     */
    @Query("match(n:Unit)-[r:unit_have_paper]->(b:Paper) where n.name={unitName} return b limit 30")
    List<PaperNode> getPaperNodeByUnitHavePaper(@Param("unitName") String unitName);


    /**
     * 通过专家姓名和论文关键词 keywords 查询论文
     * 对应模板 某专家nr 关于 关键字wk 的论文有哪些 ？
     *
     * @param expertName 专家名
     * @param keywords   关键字
     * @return
     */
    @Query("match (e:Expert)-[r:write]->(p:Paper) where p.keywords=~{keywords} and e.name={expertName} return p limit 30")
    List<PaperNode> getPaperNodeByExpertNameAndKeywords(@Param("expertName") String expertName, @Param("keywords") String keywords);

}
