package yxl.client.TestApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yxl.client.TestApp.Util.GsonUtil;
import yxl.client.TestApp.Util.TlUserUtil;
import yxl.client.TestApp.entity.Producer;
import yxl.client.TestApp.entity.Task;
import yxl.client.TestApp.entity.User;
import yxl.client.TestApp.service.UserServiceImpl;

@RestController
//@RequestMapping("/user")
public class TestController {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String Index() {
        // 在 model 中添加一个名为 "user" 的 user 对象
        /*model.addAttribute("user", user);*/
        // 返回一个字符串 " success" 作为视图名称
        return "success";
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public String login(@RequestBody User user) {
        User u;
        if ((u = userService.login(user)) == null) {
            return "error";
        }
        TlUserUtil.setThreadLocal(u);
        return GsonUtil.toJson(u);//返回登录完成之后的主页面
    }

    @RequestMapping(value = "/logon", method = RequestMethod.POST)
    @ResponseBody
    public String logon(@RequestBody User user) {
        String data = userService.logon(user);
        if ("成功".equals(data)) {
            return "ok";
        } else {
            return data;
        }
    }

    @RequestMapping(value = "/init_main", method = RequestMethod.POST)
    @ResponseBody
    public String init_main() {
        return userService.init_main();
    }


    @RequestMapping(value = "/uplevel", method = RequestMethod.POST)
    @ResponseBody
    public String uplevel(@RequestBody Producer producer) {
        return userService.uplevel(producer);
    }

    @RequestMapping(value = "/prod_datas", method = RequestMethod.POST)
    @ResponseBody
    public String prod_datas() {
        return userService.getPordTasks();
    }


    @RequestMapping(value = "/prod_task", method = RequestMethod.POST)
    @ResponseBody
    public String prod_task(@RequestBody Task t) {
        if (t.getT_id() == null || "".equals(t.getT_id()))
            return null;
        Task task = userService.getProd_task(t);
        return GsonUtil.toJson(task);
    }

    @RequestMapping(value = "/prod_getTask", method = RequestMethod.POST)
    @ResponseBody
    public String makeTask(@RequestBody Task task) {
        if (task == null)
            return null;
        return userService.prod_getTask(task);
    }

    @RequestMapping(value = "/prod/startTask", method = RequestMethod.POST)
    @ResponseBody
    public boolean startTask(@RequestBody Task task) {
        if (task.getT_id() == null || "".equals(task.getT_id()))
            return false;
        return userService.prod_startTask(task);
    }

    @RequestMapping(value = "/prod/stopTask", method = RequestMethod.POST)
    @ResponseBody
    public boolean stopTask(@RequestBody Task task) {
        if (task.getT_id() == null || "".equals(task.getT_id()))
            return false;
        return userService.prod_stopTask(task);
    }

/*    @RequestMapping(value = "/prod/endTask", method = RequestMethod.POST)
    @ResponseBody
    public boolean endTask(@RequestBody Task task) {
        if (task.getT_id() == null || "".equals(task.getT_id()))
            return false;
        return userService.prod_endTask(task);

    }*/

    @RequestMapping(value = "/cons_datas", method = RequestMethod.POST)
    @ResponseBody
    public String cons_datas() {
        return userService.getConsTasks();
    }

    @RequestMapping(value = "/cons_maketask", method = RequestMethod.POST)
    @ResponseBody
    public String cons_makedatas(@RequestBody Task task) {
        return userService.cmakeTask(task);
    }

    @RequestMapping(value = "/cons_starttask", method = RequestMethod.POST)
    @ResponseBody
    public String cons_starttask(@RequestBody Task task) {
        return userService.cStartTask(task);
    }

    @RequestMapping(value = "/cons_stopTask", method = RequestMethod.POST)
    @ResponseBody
    public String cons_stopTask(@RequestBody Task task) {
        return userService.cStopTask(task);
    }

    @RequestMapping(value = "/cons_endTask", method = RequestMethod.POST)
    @ResponseBody
    public String cons_endTask(@RequestBody Task task) {
        return userService.cEndTask(task);
    }

    @RequestMapping(value = "/cons_getTaskResult", method = RequestMethod.POST)
    @ResponseBody
    public String cons_getTaskResult(@RequestBody Task task) {
        return userService.cgetTaskResult(task);
    }
}
