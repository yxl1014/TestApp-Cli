package yxl.client.TestApp.Net.ToClient;

import yxl.client.TestApp.Util.JWTUtil;
import yxl.client.TestApp.entity.T_result;
import yxl.client.TestApp.entity.Task;
import yxl.client.TestApp.entity.User;
import yxl.client.TestApp.entity.Ut_working;
import yxl.client.TestApp.fileio.ReadFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Date;

public class ToUserByTcp implements INetToUser {
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
        Socket client = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        final T_result tResult = new T_result();
        try {
            Timestamp stime = new Timestamp(System.currentTimeMillis());
            client = new Socket(task.getT_serverip(), Integer.parseInt(task.getT_serverport()));
            outputStream = client.getOutputStream();
            String message = task.getT_context();
            outputStream.write(message.getBytes("UTF-8"));
            client.shutdownOutput();

            inputStream = client.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
                result.append(new String(bytes, 0, len, "UTF-8"));
            }
            Timestamp etime = new Timestamp(System.currentTimeMillis());
            tResult.setTr_utwid(utw.getUtw_id());
            tResult.setTr_uip(isa != null ? isa.getHostAddress() : null);
            tResult.setTr_uid(uid);
            tResult.setTr_code(200);
            tResult.setTr_isSuccess(task.getT_target().equals(result.toString()));
            tResult.setTr_target(task.getT_target());
            tResult.setTr_value(result.toString());
            tResult.setTr_message(result.toString());
            tResult.setTr_reqtime(stime);
            tResult.setTr_resptime(etime);
        } catch (IOException e) {
            tResult.setTr_code(300);
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                outputStream.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return tResult;
    }
}
