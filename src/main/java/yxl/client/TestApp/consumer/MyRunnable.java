package yxl.client.TestApp.consumer;


import org.springframework.beans.factory.annotation.Autowired;
import yxl.client.TestApp.Kafka.KafkaController;
import yxl.client.TestApp.ProtocolAdapter.ProtocolAdapter;
import yxl.client.TestApp.Util.GsonUtil;
import yxl.client.TestApp.Util.TlUserUtil;
import yxl.client.TestApp.entity.T_result;
import yxl.client.TestApp.entity.Task;
import yxl.client.TestApp.entity.User;
import yxl.client.TestApp.entity.Ut_working;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MyRunnable implements Runnable {
    private final Task task;
    private final Ut_working utw;
    private OutputStream out;
    private InputStream in;
    private Socket socket;
    private Executor pool = Executors.newSingleThreadExecutor();

    @Autowired
    private ProtocolAdapter adapter;

    @Autowired
    private KafkaController kafkaController;

    public MyRunnable(Task task, Ut_working utw) {
        this.utw = utw;
        this.task = task;
        try {
            socket = new Socket(task.getT_serverip(), Integer.parseInt(task.getT_serverport()));
            in = socket.getInputStream();
            out = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        InetAddress isa = null;
        try {
            isa = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        while (true) {
            String context = adapter.getProtocolMessage(task.getT_protocol(), task.getT_context());
            try {
                Timestamp stime = new Timestamp(new Date().getTime());
                out.write(context.getBytes(StandardCharsets.UTF_8));
                out.flush();
                byte[] result = new byte[1024];
                int len;
                StringBuilder data = new StringBuilder();
                while ((len = in.read(result)) > 0) {
                    data.append(result);
                }
                Timestamp etime = new Timestamp(new Date().getTime());

                final T_result tResult = new T_result();
                tResult.setTr_id(utw.getUtw_utid());
                tResult.setTr_utwid(utw.getUtw_id());
                tResult.setTr_uip(isa != null ? isa.getHostAddress() : null);
                tResult.setTr_uid(TlUserUtil.getThreadLocal().getU_id());
                tResult.setTr_code(data.length());
                tResult.setTr_isSuccess(data.length() != 0);
                tResult.setTr_target(task.getT_target());
                tResult.setTr_value(data.toString());
                tResult.setTr_message(data.toString());
                tResult.setTr_reqtime(stime);
                tResult.setTr_resptime(etime);
                pool.execute(new Runnable() {
                    @Override
                    public void run() {
                        kafkaController.sendToTopic1(GsonUtil.toJson(tResult));
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close(){
        try {
            this.out.close();
            this.in.close();
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
