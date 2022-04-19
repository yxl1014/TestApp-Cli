package yxl.client.TestApp.Kafka;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class KafkaController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaController(KafkaTemplate<String, String> template) {
        this.kafkaTemplate = template;
    }

    private final Executor executor = Executors.newCachedThreadPool();

    public void sendToTopic1(final String message) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                kafkaTemplate.send("topic1", message);
            }
        });
    }

    public void sendToTopic2(final String message) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                kafkaTemplate.send("topic2", message);
            }
        });
    }
}
