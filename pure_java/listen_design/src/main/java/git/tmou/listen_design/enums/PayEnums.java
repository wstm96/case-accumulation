package git.tmou.listen_design.enums;


import lombok.Getter;

//@Component  枚举类默认final修饰，无法注入spring容器中
public enum PayEnums {
    Alipay("ali", "aliPay"),
    JdPay("jd", "jdPay"),
    WexinPay("weixin", "weixinPay");
    @Getter
    private final String code;
    @Getter
    private final String msg;

    PayEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;

    }

}
