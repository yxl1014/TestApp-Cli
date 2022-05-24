package yxl.client.TestApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yxl.client.TestApp.Net.ToServer.RequestToServer;
import yxl.client.TestApp.Util.GsonUtil;
import yxl.client.TestApp.Util.LocalList;
import yxl.client.TestApp.consumer.MyRunnable;
import yxl.client.TestApp.consumer.MyThread;
import yxl.client.TestApp.entity.*;
import yxl.client.TestApp.fileio.FileImpl;

import java.io.File;
import java.util.List;

@Service
public class UserServiceImpl {
    private LocalList<Task> tasks;
    private LocalList<Task> prod_tasks;
    private LocalList<Task> cons_tasks;

    private static MyThread myThread;

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

    public String uplevel(Producer producer) {
        String data = GsonUtil.toJson(producer);
        String result = toServer.sendPost("uplevel", 1, data);
        Result rsl = GsonUtil.fromJson(result, Result.class);
        return rsl.getData();
    }

    public String logon(User user) {
        //向服务端发送请求，获取返回信息
        String data = GsonUtil.toJson(user);
        String result = toServer.sendPost("logon", 1, data);
        Result rsl = GsonUtil.fromJson(result, Result.class);

        return rsl.getData();
    }

    //初始化主页。获取所有任务，10条10条的获取
    public String init_main() {
        if (this.tasks != null) {
            return GsonUtil.toJson(this.tasks.getTasks());
        }
        //向服务端发送请求，获取返回信息
        String result = toServer.sendPost("init", 2, "");
        Result rsl = GsonUtil.fromJson(result, Result.class);
        List<Task> tasks = GsonUtil.list_TaskfromJson(rsl.getData());
        //将所有task存本地
        this.tasks = new LocalList<>(tasks);
        //默认获取10条task返回
        return GsonUtil.toJson(this.tasks.getTasks());
    }


    //消费者获取所有接受的任务
    public String getPordTasks() {
        if (this.prod_tasks != null) {
            return GsonUtil.toJson(this.prod_tasks.getTasks());
        }
        //向服务端发送请求，获取返回信息
        String result = toServer.sendPost("find_Uts", 3, "");
        Result rsl = GsonUtil.fromJson(result, Result.class);
        List<Task> tasks = GsonUtil.list_TaskfromJson(rsl.getData());

        this.prod_tasks = new LocalList<>(tasks);

        //默认获取10条Ut返回
        return GsonUtil.toJson(this.prod_tasks.getTasks());
    }

    //消费者获取某一个任务的全部信息
    public Task getProd_task(Task task) {
        String result = toServer.sendPost("find_task", 2, GsonUtil.toJson(task));
        Result rsl = GsonUtil.fromJson(result, Result.class);
        return GsonUtil.fromJson(rsl.getData(), Task.class);
    }

    //消费者开始任务
    public boolean prod_startTask(Task task) {
        String result = toServer.sendPost("startTask", 3, GsonUtil.toJson(task));//向服务端发送请求
        Result rsl = GsonUtil.fromJson(result, Result.class);//获取服务端返回报文
        String data = rsl.getData();//获取utw结果
        if (data.equals("失败")){
            return false;
        }
        Ut_working utw = GsonUtil.fromJson(data, Ut_working.class);
        boolean ok = utw != null;//如果utw不为空则成功
        if (ok) {
            myThread = new MyThread(new MyRunnable(getProd_task(task), utw));//起一条线程来向北侧段发送请求
            myThread.start();//启动线程
            return true;//返回成功
        }
        return false;
    }

    //消费者结束任务
    public boolean prod_stopTask(Task task) {
        String result = toServer.sendPost("stopTask", 3, GsonUtil.toJson(task));
        Result rsl = GsonUtil.fromJson(result, Result.class);
        String data = rsl.getData();
        boolean ok = data.equals("成功");
        if (ok) {
            if (myThread != null)//停止向被测试者发送请求
                myThread.stop0();
        }
        return ok;
    }


    // 消费者接受任务
    public String prod_getTask(Task task) {
        String result = toServer.sendPost("getTask", 3, GsonUtil.toJson(task));
        Result rsl = GsonUtil.fromJson(result, Result.class);
        return rsl.getData();
    }


    //生产者获取所有生产的任务
    public String getConsTasks() {
        if (this.cons_tasks != null) {
            return GsonUtil.toJson(this.cons_tasks.getTasks());
        }
        String result = toServer.sendPost("getMyTask", 4, "");
        Result rsl = GsonUtil.fromJson(result, Result.class);
        List<Task> tasks = GsonUtil.list_TaskfromJson(rsl.getData());
        //将所有task存本地
        this.cons_tasks = new LocalList<>(tasks);
        //默认获取10条task返回
        return GsonUtil.toJson(this.cons_tasks.getTasks());
    }

    public String cmakeTask(Task task) {
        String result = toServer.sendPost("makeTask", 4, GsonUtil.toJson(task));
        Result rsl = GsonUtil.fromJson(result, Result.class);
        return rsl.getData();
    }

    public String cStartTask(Task task) {
        String result = toServer.sendPost("startTask", 4, GsonUtil.toJson(task));
        Result rsl = GsonUtil.fromJson(result, Result.class);
        return rsl.getData();
    }

    public String cStopTask(Task task) {
        String result = toServer.sendPost("stopTask", 4, GsonUtil.toJson(task));
        Result rsl = GsonUtil.fromJson(result, Result.class);
        return rsl.getData();
    }

    public String cEndTask(Task task) {
        String result = toServer.sendPost("endTask", 4, GsonUtil.toJson(task));
        Result rsl = GsonUtil.fromJson(result, Result.class);
        return rsl.getData();
    }


    public String cgetTaskResult(Task task) {
        String result = toServer.sendPost("getTaskResult", 4, GsonUtil.toJson(task));
        Result rsl = GsonUtil.fromJson(result, Result.class);
        return rsl.getData();
    }
}
