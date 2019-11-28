package cn.tcualhp.tech_kg.testCode;

import cn.tcualhp.tech_kg.utils.LoadJsonUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author jerry
 * @program tech_kg
 * @package_name cn.tcualhp.tech_kg.testCode
 * @description 生成用户自定义词典
 * @create 2019/11/28 19:09
 **/

public class GenerateCustomDictionary {
    private JSONObject jsonObject = null;

    public GenerateCustomDictionary() {
        try {
            this.jsonObject = LoadJsonUtil.getJsonObject("customDictionary/developmentVersion1.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateDictionary(JSONObject jsonObject) {
        JSONArray records = jsonObject.getJSONArray("RECORDS");
        Set<String> expertNameSet = new HashSet<>();
        Set<String> unitNameSet = new HashSet<>();
        Set<String> keywordsSet = new HashSet<>();
        for (int i = 0; i < records.size(); i++) {
            String expertName = records.getJSONObject(i).getString("first_author");
            String unitName = records.getJSONObject(i).getString("unit");
            String keyword = records.getJSONObject(i).getString("keywords");
            /**
             * 处理专家姓名
             */
            if (expertName != "") {
                expertNameSet.add(expertName);
            }

            /**
             * 处理单位
             */
            if (unitName != "") {
                String[] unitNames = unitName.split(";");
                for (String s : unitNames) {
                    if (s != ";" && s!= ""){
                        unitNameSet.add(s);
                    }
                }
            }

            /**
             * 处理 论文 关键字
             */
            if (keyword != "") {
                String[] keywords = keyword.split(";");
                for (String s : keywords) {
                    if (s != ";" && s!= ""){
                        keywordsSet.add(s);
                    }
                }
            }
        }
        try {
            String path = "src/main/resources/customDictionary/";
            String name = "expertName.txt";
            File file = new File(path + name);
            file.createNewFile();
            int count = 0;
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (String s : expertNameSet) {
                bufferedWriter.write(s + "\r\n");
                bufferedWriter.flush();
                count++;
            }
            bufferedWriter.close();
            System.out.println(count);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try {
            String path = "src/main/resources/customDictionary/";
            String name = "keywords.txt";
            File file = new File(path + name);
            file.createNewFile();
            int count = 0;
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (String s : keywordsSet) {
                bufferedWriter.write(s + "\r\n");
                bufferedWriter.flush();
                count++;
            }
            bufferedWriter.close();
            System.out.println(count);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try {
            String path = "src/main/resources/customDictionary/";
            String name = "unitNames.txt";
            File file = new File(path + name);
            file.createNewFile();
            int count = 0;
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (String s : unitNameSet) {
                bufferedWriter.write(s + "\r\n");
                bufferedWriter.flush();
                count++;
            }
            bufferedWriter.close();
            System.out.println(count);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }


    public static void main(String[] args) {
        GenerateCustomDictionary generateCustomDictionary = new GenerateCustomDictionary();
        generateCustomDictionary.generateDictionary(generateCustomDictionary.jsonObject);
    }
}
