package git.tmou.listen_design.controller;


import git.tmou.listen_design.service.impl.PayService1;
import git.tmou.listen_design.service.impl.PayService2;
import git.tmou.listen_design.service.impl.PayService3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PayController {
    @Autowired
    private PayService1 payService1;
    @Autowired
    private PayService2 payService2;
    @Autowired
    private PayService3 payService3;

    @RequestMapping("/pay/{code}")
    @ResponseBody
    public String payController1(@PathVariable String code) {
        payService1.pay(code);
        return "支付成功";
    }

    @RequestMapping("/pay2/{code}")
    @ResponseBody
    public String payController2(@PathVariable String code) {
        payService2.pay(code);
        return "支付成功";
    }

    @RequestMapping("/pay3/{code}")
    @ResponseBody
    public String payController3(@PathVariable String code) {
        payService3.pay(code);
        return "支付成功";
    }
}
