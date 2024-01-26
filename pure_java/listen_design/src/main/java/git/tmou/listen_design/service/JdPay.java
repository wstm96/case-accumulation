package git.tmou.listen_design.service;

import git.tmou.listen_design.annotation.PayAnnotation;
import org.springframework.stereotype.Component;

@Component
@PayAnnotation(code = "jd", msg = "京东Pay")
public class JdPay implements Ipay {
    @Override
    public void pay() {
        System.out.println("京东Pay");
    }
}
