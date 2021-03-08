package com.zsw;

import com.zsw.client.BioClient;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhangShaowei on 2021/3/4 17:17
 */
@Slf4j
public class StaticTests {

    public static void main(String[] args) {
        new BioClient("127.0.0.1", 8081).start();
    }

}
