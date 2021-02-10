package com.zsw.proxy;

/**
 * @author ZhangShaowei on 2021/2/10 13:28
 */
public class ChengduShopService implements ShopService {
    @Override
    public String pack(String product) {
        System.out.println(this.getClass());
        return "[" + product + "]";
    }
}
