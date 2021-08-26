package yxl.client.TestApp.ProtocolAdapter;

import org.springframework.stereotype.Service;

@Service
public class ProtocolAdapter {
    public String getProtocolMessage(String protocol,String context){
        return context;
    }
}
