package cn.codeprobe.community.controller;

import cn.codeprobe.community.entity.User;
import cn.codeprobe.community.service.UserService;
import cn.codeprobe.community.utils.ActivationPag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class LoginController implements ActivationPag {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String getRegisterPage() {
        return "/site/register";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String getLoginPage() {
        return "/site/login";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(Model model, User user) {
        Map<String, Object> map = userService.register(user);
        if (map == null || map.isEmpty()) {
            model.addAttribute("msg", "注册成功,我们已经向您的邮箱发送了一份激活邮件,请尽快前往激活!");
            model.addAttribute("target", "/index");
            return "/site/operate-result";
        } else {
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            model.addAttribute("emailMsg", map.get("emailMsg"));
            return "/site/register";
        }
    }

    @RequestMapping(path = "/activation/{userId}/{code}", method = RequestMethod.GET)
    public String activation(Model model, @PathVariable("userId") int userId, @PathVariable("code") String code) {
        int result = userService.activation(userId, code);
        switch (result){
            case ACTIVATION_SUCCESS:
                model.addAttribute("msg", "账户激活成功,您的账号已经可以正常使用! 即将为您跳转到登陆界面!");
                model.addAttribute("target", "/login");
                break;
            case ACTIVATION_FAILURE:
                model.addAttribute("msg", "账户激活失败,您提供的激活码不正确!");
                model.addAttribute("target", "/register");
                break;
            case ACTIVATION_REPEAT:
                model.addAttribute("msg", "无效操作！该账户已经激活过了!");
                model.addAttribute("target", "/index");
                break;
        }
        return "/site/operate-result";
    }
}
