package yxl.client.TestApp.Kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class DemoController {

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;


    @RequestMapping("/test")
    public String demo(@RequestParam("message") String message) {
        kafkaTemplate.send("topic1", message).addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
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
