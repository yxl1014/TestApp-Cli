package yxl.client.TestApp.Net.ToClient;

import org.springframework.stereotype.Component;
import yxl.client.TestApp.Util.JWTUtil;
import yxl.client.TestApp.entity.T_result;
import yxl.client.TestApp.entity.Task;
import yxl.client.TestApp.entity.User;
import yxl.client.TestApp.entity.Ut_working;
import yxl.client.TestApp.fileio.ReadFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Date;

@Component
public class ToUserByHttp implements INetToUser {

/*    @Autowired
    private FileImpl file;*/

    @Override
    public T_result sendMessage(Task task, Ut_working utw) {
        String token = new ReadFile().readFile("user_token.txt").get(0);
        User user = JWTUtil.unsign(token, User.class);
        String uid = user.getU_id();
        //String uid = "20211119121400Yk48";
        InetAddress isa = null;
        StringBuilder result = new StringBuilder();
        try {
            isa = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        BufferedWriter out = null;
        BufferedReader in = null;
        final T_result tResult = new T_result();
        //String context = adapter.getProtocolMessage(task.getT_protocol(), task.getT_context());
        try {
            Timestamp stime = new Timestamp(System.currentTimeMillis());
            URL realUrl = new URL("http://" + task.getT_serverip() + ":" + task.getT_serverport() + task.getT_serverurl());
            //URL realUrl = new URL("http://127.0.0.1:9999/");
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.connect();
            out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8));
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            Timestamp etime = new Timestamp(System.currentTimeMillis());
            tResult.setTr_utwid(utw.getUtw_id());
            tResult.setTr_uip(isa != null ? isa.getHostAddress() : null);
            tResult.setTr_uid(uid);
            tResult.setTr_code(connection.getResponseCode());
            tResult.setTr_isSuccess(task.getT_target().equals(result.toString()));
            tResult.setTr_target(task.getT_target());
            tResult.setTr_value(result.toString());
            tResult.setTr_message(result.toString());
            tResult.setTr_reqtime(stime);
            tResult.setTr_resptime(etime);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tResult;
    }
}
