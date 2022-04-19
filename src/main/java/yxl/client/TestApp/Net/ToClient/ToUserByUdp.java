package yxl.client.TestApp.Net.ToClient;

import yxl.client.TestApp.Util.JWTUtil;
import yxl.client.TestApp.entity.T_result;
import yxl.client.TestApp.entity.Task;
import yxl.client.TestApp.entity.User;
import yxl.client.TestApp.entity.Ut_working;
import yxl.client.TestApp.fileio.ReadFile;

import java.io.IOException;
import java.net.*;
import java.sql.Timestamp;
import java.util.Date;

public class ToUserByUdp implements INetToUser {
    @Override
    public T_result sendMessage(Task task, Ut_working utw) {
        String token = new ReadFile().readFile("user_token.txt").get(0);
        User user = JWTUtil.unsign(token, User.class);
        if (user == null)
            return null;
        String uid = user.getU_id();
        //String uid = "20211119121400Yk48";
        InetAddress isa = null;
        //StringBuilder result = new StringBuilder();
        try {
            isa = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        DatagramSocket ds = null; //建立通讯socket
        final T_result tResult = new T_result();
        try {
            Timestamp stime = new Timestamp(new Date().getTime());
            ds = new DatagramSocket();
            byte[] bys = task.getT_context().getBytes();
            DatagramPacket dp = new DatagramPacket(bys, bys.length, InetAddress.getByName(task.getT_serverip()), Integer.parseInt(task.getT_serverport()));//建立数据包，声明长度，接收端主机，端口号
            ds.send(dp);//发送数据
            Timestamp etime = new Timestamp(new Date().getTime());
            tResult.setTr_utwid(utw.getUtw_id());
            tResult.setTr_uip(isa != null ? isa.getHostAddress() : null);
            tResult.setTr_uid(uid);
            tResult.setTr_code(200);
            tResult.setTr_isSuccess(true/*task.getT_target().equals(result.toString())*/);
            tResult.setTr_target(task.getT_target());
            //tResult.setTr_value(result.toString());
            //tResult.setTr_message(result.toString());
            tResult.setTr_reqtime(stime);
            tResult.setTr_resptime(etime);
        } catch (IOException e) {
            tResult.setTr_code(300);
            e.printStackTrace();
        } finally {
            ds.close();
        }

        return tResult;
    }
}
