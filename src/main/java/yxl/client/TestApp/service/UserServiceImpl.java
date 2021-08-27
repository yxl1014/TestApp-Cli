package yxl.client.TestApp.service;

import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yxl.client.TestApp.Net.RequestToServer;
import yxl.client.TestApp.Util.GsonUtil;
import yxl.client.TestApp.Util.LocalList;
import yxl.client.TestApp.entity.Result;
import yxl.client.TestApp.entity.Task;
import yxl.client.TestApp.entity.User;
import yxl.client.TestApp.entity.Ut;
import yxl.client.TestApp.fileio.FileImpl;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImpl {
    private LocalList<Task> tasks;
    private LocalList<Ut> uts;

    @Autowired
    private RequestToServer toServer;

    @Autowired
    private FileImpl file;

    public User login(User user) {
        //向服务端发送请求，获取返回信息
        String data = GsonUtil.toJson(user);
        String result = toServer.sendPost("login", 1, data);
        Result rsl = GsonUtil.fromJson(result, Result.class);

        //获取登陆后的用户
        User n = GsonUtil.fromJson(rsl.getData(), User.class);
        if (n == null)//若为空则登录失败
            return null;
        else {//若不为空
            //找个地方存token,读写文件
            File token = file.createFile("user_token.txt");
            file.writeFile(token, rsl.getToken());
            //返回user
            return user;
        }
    }

    public String logon(User user) {
        //向服务端发送请求，获取返回信息
        String data = GsonUtil.toJson(user);
        String result = toServer.sendPost("logon", 1, data);
        Result rsl = GsonUtil.fromJson(result, Result.class);

        return rsl.getData();
    }

    public String init_main() {
        if (this.tasks!=null){
            return GsonUtil.toJson(this.tasks.getTasks());
        }
        //向服务端发送请求，获取返回信息
        String result=toServer.sendPost("init",2,"");
        Result rsl = GsonUtil.fromJson(result, Result.class);
        List<Task> tasks=GsonUtil.list_TaskfromJson(rsl.getData());
        //将所有task存本地
        this.tasks=new LocalList<>(tasks);
        //默认获取10条task返回
        return GsonUtil.toJson(this.tasks.getTasks());
    }

    public String getUts() {
        if (this.uts!=null){
            return GsonUtil.toJson(this.uts.getTasks());
        }
        //向服务端发送请求，获取返回信息
        String result=toServer.sendPost("getMyTask",2,"");
        Result rsl = GsonUtil.fromJson(result, Result.class);
        List<Ut> uts=GsonUtil.list_UtfromJson(rsl.getData());

        //将所有Ut存本地
        this.uts=new LocalList<>(uts);
        //默认获取10条Ut返回
        return GsonUtil.toJson(this.uts.getTasks());
    }
}
