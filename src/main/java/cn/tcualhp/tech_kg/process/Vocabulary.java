package cn.tcualhp.tech_kg.process;

import cn.tcualhp.tech_kg.utils.LoadJsonUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.*;

/**
 * @author jerry
 * @program tech_kg
 * @package_name cn.tcualhp.tech_kg.process
 * @description 词语库
 * @create 2019/11/27 14:11
 **/
@Data
@NoArgsConstructor
public class Vocabulary {
    /**
     * 注意这个id没用，没用，没用
     * 因为会有重复
     * 并且后面的id会重新指定
     * 因为id关系到数组长度
     */
    private long id;

    /**
     * 词的值，唯一，去重依据
     */
    private String value;

    /**
     * 词的词性
     */
    private String type;

    /**
     * 返回指定词汇 json 文件下的词汇的 list
     * @param filename 词汇文件名
     * @return  list 全部词汇的 list
     * @throws IOException
     */
    public Set<Vocabulary> getVocabularySet(String filename) throws IOException {
        JSONObject jsonObject = LoadJsonUtil.getJsonObject(filename);
        JSONArray vocabulary = jsonObject.getJSONArray("vocabulary");
        Set<Vocabulary> vocabularies = new HashSet<>();
        List<Vocabulary> vocabularyList = new ArrayList<>();
        for (int i = 0;i < vocabulary.size();i++) {
            Vocabulary v = new Vocabulary();
            v.id = i + 1;
            v.value = vocabulary.getJSONObject(i).getString("value");
            v.type = vocabulary.getJSONObject(i).getString("type");
            vocabularies.add(v);
        }
        return vocabularies;
    }

    /**
     * 判断是否重复，词就是唯一值
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vocabulary that = (Vocabulary) o;
        return value.equals(that.value);
    }

    /**
     * 哈希，和id与type无关
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public static void main(String[] args) throws IOException {
        Vocabulary vocabulary = new Vocabulary();
        Set<Vocabulary>  vocabularies=  vocabulary.getVocabularySet("vocabulary/vocabulary.json");
        for (Vocabulary v : vocabularies) {
            System.out.println(v.id + v.value + v.type);
        }
    }
}
