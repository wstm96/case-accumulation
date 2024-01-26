package git.tmou.listen_design.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PayService2 {

    @Autowired
    AliPay aliPay;
    @Autowired
    JdPay jdPay;
    @Autowired
    WechatPay wechatPay;

    public void pay(String code) {
//        PayEnums target = Arrays.stream(PayEnums.values()).filter(payEnums -> payEnums.getCode().equals(code)).findFirst().orElse(null);
////        PayEnums target =
////                Arrays.stream(PayEnums.values()).filter(payEnums -> payEnums.getCode().equals(code)).findFirst().orElse(null);
//        if (target != null) {
//            log.info("-----payv2枚举支付:{}", target.getCode() + "\t" + target.getMsg());
//        } else {
//            log.info("-----payv2枚举支付:{}", code + "\t" + "没有获得对应的支付方式");
//        }
    }
}
