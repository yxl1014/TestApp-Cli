package yxl.client.TestApp.Kafka;

import org.jetbrains.annotations.NotNull;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;

@Service
public class KafkaController {

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;


    public String sendToTopic1(String message) {
        kafkaTemplate.send("topic1", message).addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(@NotNull Throwable ex) {
                System.out.println("发送失败,error:"+ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                System.out.println("发送成功");
            }
        });
        return message;
    }

    public String sendToTopic2(String message) {
        kafkaTemplate.send("topic2", message).addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(@NotNull Throwable ex) {
                System.out.println("发送失败,error:"+ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                System.out.println("发送成功");
            }
        });
        return message;
    }
}
