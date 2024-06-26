package org.csu.mypetstore.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("org.csu.mypetstore.api.persistence")
public class MypetsotreApiDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MypetsotreApiDemoApplication.class, args);
    }
}
