package com.sjd.test.httpRequest;

import com.sjd.test.util.HttpTool;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Auther sunjid
 * @Date 2019/9/7
 * @Date http请求测试类
 **/
public class TestHttp {

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://pic.baike.soso.com/p/20090711/20090711101754-314944703.jpg");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //此处需指定文件名称，否则会报拒绝访问  异常nel4
        File file = new File("D:\\sjd\\data\\a.jpg");
        HttpTool.streamToFile(connection,file);
    }

}
