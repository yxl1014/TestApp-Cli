package yxl.client.TestApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yxl.client.TestApp.Net.RequestToServer;
import yxl.client.TestApp.Util.GsonUtil;
import yxl.client.TestApp.entity.Result;
import yxl.client.TestApp.entity.User;
import yxl.client.TestApp.fileio.FileImpl;

import java.io.File;
import java.io.IOException;

@Service
public class UserServiceImpl {
    @Autowired
    private RequestToServer toServer;

    @Autowired
    private FileImpl file;

    public User login(User user){
        String data= GsonUtil.toJson(user);
        String result=toServer.sendPost("login",1,data);
        Result rsl=GsonUtil.fromJson(result,Result.class);

        User n=GsonUtil.fromJson(rsl.getData(),User.class);
        if(n==null)
            return null;
        else {
            //找个地方存token,读写文件
            File token=file.createFile("user_token.txt");
            file.writeFile(token, rsl.getToken());
            return user;
        }
    }
}
