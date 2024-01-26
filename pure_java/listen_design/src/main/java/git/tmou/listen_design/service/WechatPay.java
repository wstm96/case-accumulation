package git.tmou.listen_design.service;

import git.tmou.listen_design.annotation.PayAnnotation;
import org.springframework.stereotype.Component;

@Component
@PayAnnotation(code = "weixin", msg = "微信Pay")
public class WechatPay implements Ipay {

    @Override
    public void pay() {
        System.out.println("微信Pay");
    }
}
