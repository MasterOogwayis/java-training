package com.zsw.proxy;

/**
 * @author ZhangShaowei on 2021/2/10 13:27
 */
public interface ShopService {

    default String buy(String productName) {
        System.out.println(this.getClass());
        return pack(productName);
    }


    String pack(String product);

}
