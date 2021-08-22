package yxl.client.TestApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yxl.client.TestApp.Net.RequestToServer;
import yxl.client.TestApp.Util.GsonUtil;
import yxl.client.TestApp.Util.JWTUtil;
import yxl.client.TestApp.entity.Result;
import yxl.client.TestApp.entity.User;

@Service
public class UserServiceImpl {
    @Autowired
    private RequestToServer toServer;

    public boolean login(User user){
        String data= GsonUtil.toJson(user);
        String result=toServer.sendPost("login",1,data);
        Result rsl=GsonUtil.fromJson(result,Result.class);

        User n=GsonUtil.fromJson(rsl.getData(),User.class);
        if(n==null)
            return false;
        else {
            //找个地方存token,读写文件
            ThreadLocal<String> token=new ThreadLocal<>();
            token.set(rsl.getToken());
            return true;
        }
    }
}
