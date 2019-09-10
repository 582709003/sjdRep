package cn.com.zhsz.eco.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.ClassPathResource;

import java.util.Scanner;

/**
 * 读取返回码
 */
public class StepExecutor{

    private StepExecutor() { startStreamTask(); }
    private static StepExecutor instance = new StepExecutor();
    public static StepExecutor getInstance() {return instance;}

    public JSONObject jsonObject;

    public String get(String key) {
        if(jsonObject == null){
            startStreamTask();
        }
        return jsonObject.containsKey(key)?jsonObject.getString(key):null;
    }

    public void startStreamTask(){
        ClassPathResource resource = new ClassPathResource("global.json");
        if (resource.exists()) {
            String jsonData = jsonRead(resource);
            jsonObject = JSONObject.parseObject(jsonData);
        }
    }
    private String jsonRead(ClassPathResource file){
        Scanner scanner = null;
        StringBuilder buffer = new StringBuilder();
        try {
            scanner = new Scanner(file.getInputStream(), "utf-8");
            while (scanner.hasNextLine()) {
                buffer.append(scanner.nextLine());
            }
        } catch (Exception e) {

        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return buffer.toString();
    }
}