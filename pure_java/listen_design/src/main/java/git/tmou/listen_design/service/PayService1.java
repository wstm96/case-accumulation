package git.tmou.listen_design.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayService1 {

    @Autowired
    AliPay aliPay;
    @Autowired
    JdPay jdPay;
    @Autowired
    WechatPay wechatPay;

    public void pay(String code) {
        switch (code) {
            case "ali":
                aliPay.pay();
                break;
            case "jd":
                jdPay.pay();
                break;
            case "weixin":
                wechatPay.pay();
                break;
            default:
                break;
        }
    }
}
