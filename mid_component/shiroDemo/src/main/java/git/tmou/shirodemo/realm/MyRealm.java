package git.tmou.shirodemo.realm;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import git.tmou.shirodemo.entity.User;
import git.tmou.shirodemo.serveice.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    //自定义授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    //自定义登录认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        Object principal = token.getPrincipal();
        String username = principal.toString();
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getName, username));
        if (user != null) {
            AuthenticationInfo info = new SimpleAuthenticationInfo(principal, user.getPwd(), ByteSource.Util.bytes("salt"), username);
            return info;
        }
        return null;
    }
}
