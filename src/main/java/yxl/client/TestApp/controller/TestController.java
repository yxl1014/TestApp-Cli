package yxl.client.TestApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yxl.client.TestApp.Util.GsonUtil;
import yxl.client.TestApp.entity.User;
import yxl.client.TestApp.service.UserServiceImpl;

@RestController
//@RequestMapping("/user")
public class TestController {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public String Index() {
        // 在 model 中添加一个名为 "user" 的 user 对象
        /*model.addAttribute("user", user);*/
        // 返回一个字符串 " success" 作为视图名称
        return "success";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestBody User user) {
        ;
        User u;
        if ((u = userService.login(user)) == null) {
            return "error";
        }
        ThreadLocal<User> tUser = new ThreadLocal<>();
        tUser.set(u);
        return /*"main"*/GsonUtil.toJson(u);//返回登录完成之后的主页面
    }

    @RequestMapping(value = "/logon", method = RequestMethod.POST)
    @ResponseBody
    public String logon(@RequestBody User user) {
        String data = userService.logon(user);
        if ("成功".equals(data)) {
            return "login";
        } else {
            return data;
        }
    }

    @RequestMapping(value = "/init_main", method = RequestMethod.POST)
    @ResponseBody
    public String init_main() {
        return userService.init_main();
    }

    @RequestMapping(value = "/prod_datas", method = RequestMethod.POST)
    @ResponseBody
    public String prod_datas() {
        return userService.getUts();
    }
}
