package git.tmou.listen_design.service.impl;

import git.tmou.listen_design.annotation.PayAnnotation;
import git.tmou.listen_design.service.Ipay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class PayService3 implements ApplicationListener<ContextRefreshedEvent> {

    static Map<String, Ipay> map = null;

    public void pay(String code) {
        map.get(code).pay();
    }


    /**
     * Map<String, Object>
     *
     * @PayAnnotation(myCode = "jd")
     * public class JdPayService implements IPay
     * <p>
     * String(key) = jdPayService  类名小写
     * Object(value) = com.atguigu.review.service.JdPayService
     */

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(PayAnnotation.class);
        if (!beansWithAnnotation.isEmpty()) {
            map = new ConcurrentHashMap<>(beansWithAnnotation.size());
//            beansWithAnnotation.forEach((a, b) -> System.out.println(a + ":" + b));
            beansWithAnnotation.forEach((k, v) -> {
                String code = v.getClass().getDeclaredAnnotation(PayAnnotation.class).code();
                map.put(code, (Ipay) v);
            });
//            System.out.println("==============");
//            map.forEach((k, v) -> System.out.println(k + ":" + v));
        }
        System.out.println("demo01生效！");
    }


}
