package cn.tcualhp.tech_kg.testCode;

import cn.tcualhp.tech_kg.utils.LoadJsonUtil;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

/**
 * @author jerry
 * @program tech_kg
 * @package_name cn.tcualhp.tech_kg.testCode
 * @description 生成用户自定义词典
 * @create 2019/11/28 19:09
 **/

public class generateCustomDictionary {
    private JSONObject jsonObject = null;

    public generateCustomDictionary( ) {
        try {
            LoadJsonUtil.getJsonObject("customDictionary/developmentVersion1.json");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void generateExpertName(JSONObject jsonObject) {
//        String expertName = null;
//        JSONArray records = jsonObject.getJSONArray("RECORDS");
//        Set<> vocabularies = new HashSet<>();
//        List<Vocabulary> vocabularyList = new ArrayList<>();
//        for (int i = 0; i < vocabulary.size(); i++) {
//            Vocabulary v = new Vocabulary();
//            v.id = i + 1;
//            v.value = vocabulary.getJSONObject(i).getString("value");
//            v.type = vocabulary.getJSONObject(i).getString("type");
//            vocabularies.add(v);
//        }
//        return vocabularies;
    }
}
