package git.tmou.shirodemo.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.shiro.mgt.SecurityManager;

public class LoginTest {
    private static final Logger log = LoggerFactory.getLogger(LoginTest.class);
    public static void main(String[] args) {
        //初始化获取SecurityManager
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:userinfo.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        //2获取Subject对象
        Subject subject = SecurityUtils.getSubject();
        //3创建token对象，web应用用户名密码从页面传递
        AuthenticationToken token = new UsernamePasswordToken("zhang3", "zhang3");
        //4完成登录
        try {
            subject.login(token);
            log.info("登录成功");
            boolean r1 = subject.hasRole("role1");
            log.info("The user has role1 permission !");

        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户不存在");
        } catch (
                IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("密码错误");
        } catch (AuthenticationException ae) {
            //unexpected condition? error? }
        }

    }
}