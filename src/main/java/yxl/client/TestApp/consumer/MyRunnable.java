package yxl.client.TestApp.consumer;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import yxl.client.TestApp.Kafka.KafkaController;
import yxl.client.TestApp.Net.ToClient.*;
import yxl.client.TestApp.Util.GsonUtil;
import yxl.client.TestApp.config.KafkaConfig;
import yxl.client.TestApp.entity.Task;
import yxl.client.TestApp.entity.Ut_working;

import java.io.*;
import java.net.*;


public class MyRunnable implements Runnable {
    private final Task task;
    private final Ut_working utw;

    private INetToUser net;

    private final KafkaController kafkaController;

    public MyRunnable(Task task, Ut_working utw) {
        this.utw = utw;
        this.task = task;
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(KafkaConfig.class);
        this.kafkaController = context.getBean(KafkaController.class);
        ProTocalTypeEnum type = ProTocalTypeEnum.valueOf(task.getT_protocol().toUpperCase());
        switch (type) {
            case HTTP:
                net = new ToUserByHttp();
                break;
            case TCP:
                net = new ToUserByTcp();
                break;
            case UDP:
                net = new ToUserByUdp();
                break;
        }
    }

    @Override
    public void run() {
        while (true) {
            kafkaController.sendToTopic1(GsonUtil.toJson(net.sendMessage(this.task, this.utw)));
        }
    }

    public void close() {
    }
}
