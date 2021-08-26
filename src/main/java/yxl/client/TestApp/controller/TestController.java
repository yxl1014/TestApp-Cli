package yxl.client.TestApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import yxl.client.TestApp.entity.User;
import yxl.client.TestApp.service.UserServiceImpl;

@Controller
//@RequestMapping("/user")
public class TestController {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/")
    public String Index(@ModelAttribute("form") User user, Model model) {
        // 在 model 中添加一个名为 "user" 的 user 对象
        /*model.addAttribute("user", user);*/
        // 返回一个字符串 " success" 作为视图名称
        return "success";
    }

    @RequestMapping(value = "/login")
    public String login(String userTel, String userPwd, Model model) {
        // 在 model 中添加一个名为 "user" 的 user 对象
        User user = new User();
        //model.addAttribute("user", user);
        user.setU_tel(userTel);
        user.setU_password(userPwd);
        // 返回一个字符串 " success" 作为视图名称
        User u;
        if ((u = userService.login(user)) == null) {
            return "error";
        }
        model.addAttribute(u);
        ThreadLocal<User> tUser=new ThreadLocal<>();
        tUser.set(u);
        return "success";
    }
}
