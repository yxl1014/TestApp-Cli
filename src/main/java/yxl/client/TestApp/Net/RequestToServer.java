package yxl.client.TestApp.Net;

import org.springframework.stereotype.Component;

import javax.json.JsonObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


@Component
public class RequestToServer {
    private static final String _URL = "http://localhost:8080/";
    /*public static String userToken = null;
    public static String problemName = null;*/

    public String sendPost(String sufUrl, int num, String data) {
        DataOutputStream out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(_URL + chooseType(num) + sufUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.connect();
            out = new DataOutputStream(connection.getOutputStream());
            // 发送请求参数，防止中文乱码
            out.write(data.getBytes(StandardCharsets.UTF_8));
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！");
            e.printStackTrace();
        }
//使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        System.out.println(result);
        return result.toString();
    }

    private String chooseType(int num) {
        switch (num) {
            case 1:
                return "users/";
            case 2:
                return "tasks/";
            case 3:
                return "tasks/prod/";
            case 4:
                return "tasks/cons/";
            default:
                return "";
        }
    }
}

