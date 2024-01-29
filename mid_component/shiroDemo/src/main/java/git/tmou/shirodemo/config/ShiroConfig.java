package git.tmou.shirodemo.config;

import git.tmou.shirodemo.realm.MyRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ShiroConfig {
    @Autowired
    private MyRealm myRealm;

    @Bean("securityManager")
//    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {
//        //1创建defaultWebSecurityManager对象
//        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
//        //2创建认证对象，并设置认证策略
//        ModularRealmAuthenticator modularRealmAuthenticator = new ModularRealmAuthenticator();
//        modularRealmAuthenticator.setAuthenticationStrategy(new AllSuccessfulStrategy());
//        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
//        modularRealmAuthenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
//        defaultWebSecurityManager.setAuthenticator(modularRealmAuthenticator);
//        //3封装myRealm集合
//        List<Realm> list = new ArrayList<>(); list.add(myRealm); list.add(myRealm2);
//        //4将myRealm存入defaultWebSecurityManager对象
//        defaultWebSecurityManager.setRealms(list);
//        //5返回
//        return defaultWebSecurityManager;

        //1创建defaultWebSecurityManager对象
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //2创建加密对象，并设置相关属性 采用md5加密 迭代加密次数
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(3);
        //3将加密对象存储到myRealm中
        //4将myRealm存入defaultWebSecurityManager对象
        myRealm.setCredentialsMatcher(matcher);
        defaultWebSecurityManager.setRealm(myRealm);
        //5返回
        return defaultWebSecurityManager;
    }

    //配置Shiro内置过滤器拦截范围
    @Bean
    public DefaultShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        //设置不认证可以访问的资源
        definition.addPathDefinition("/shiro/userLogin", "anon");
        definition.addPathDefinition("/shiro/login", "anon");
//        definition.addPathDefinition("/login", "anon");
        //设置需要进行登录认证的拦截范围
        definition.addPathDefinition("/**", "authc");
        return definition;

    }
}