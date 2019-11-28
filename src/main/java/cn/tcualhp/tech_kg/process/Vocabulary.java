package cn.tcualhp.tech_kg.process;

import cn.tcualhp.tech_kg.utils.LoadJsonUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
     * 词的 id 唯一
     */
    private long id;

    /**
     * 词的值
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
    public List<Vocabulary> getVocabularyList(String filename) throws IOException {
        JSONObject jsonObject = LoadJsonUtil.getJsonObject(filename);
        JSONArray vocabulary = jsonObject.getJSONArray("vocabulary");
        List<Vocabulary> vocabularyList = new ArrayList<>();
//        HashMap<Integer,String> hashMap = new HashMap();
        for (int i = 0;i < vocabulary.size();i++) {
            Vocabulary v = new Vocabulary();
            v.id = i + 1;
            v.value = vocabulary.getJSONObject(i).getString("value");
            v.type = vocabulary.getJSONObject(i).getString("type");
            vocabularyList.add(v);
        }
        return vocabularyList;
    }

    public static void main(String[] args) throws IOException {
        Vocabulary vocabulary = new Vocabulary();
        List<Vocabulary> list =  vocabulary.getVocabularyList("vocabulary/vocabulary.json");
        for (Vocabulary v : list) {
            System.out.println(v.id + v.value + v.type);
        }
    }
}
