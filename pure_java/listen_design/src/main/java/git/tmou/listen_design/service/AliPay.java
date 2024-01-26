package git.tmou.listen_design.service;

import git.tmou.listen_design.annotation.PayAnnotation;
import org.springframework.stereotype.Component;

@Component
@PayAnnotation(code = "ali", msg = "阿里pay")
public class AliPay implements Ipay {
    @Override
    public void pay() {
        System.out.println("阿里pay");
    }
}
