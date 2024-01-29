package git.tmou.shirodemo.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
@Controller
@RequestMapping("/shiro")
public class LoginController {

    @GetMapping("/userLogin")
    public String userLogin(String name, String pwd, HttpSession session) {
        //1获取Subject对象
        Subject subject = SecurityUtils.getSubject();
        //2封装请求数据到token对象中
        AuthenticationToken token = new UsernamePasswordToken(name, pwd);
        //3调用login方法进行登录认证
        try {
            subject.login(token);
            session.setAttribute("user", token.getPrincipal().toString());
            return "main";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            System.out.println("登录失败");
            return "登录失败";
        }
    }

    //跳转到登录页面
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
