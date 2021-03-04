package com.zsw;

import com.zsw.server.netty.NettyServer;

/**
 * @author ZhangShaowei on 2021/3/4 17:17
 */
public class StaticTests {

    public static void main(String[] args) {
        NettyServer server = new NettyServer(8081);
        server.start();
    }

}
