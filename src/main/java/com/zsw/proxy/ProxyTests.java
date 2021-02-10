package com.zsw.proxy;

/**
 * @author ZhangShaowei on 2021/2/10 13:29
 */
public class ProxyTests {

    public static void main(String[] args) {
        ShopService shopService = new JdkProxy().getInstance(new ChengduShopService());

        System.out.println(shopService.buy("苹果"));

    }

}
