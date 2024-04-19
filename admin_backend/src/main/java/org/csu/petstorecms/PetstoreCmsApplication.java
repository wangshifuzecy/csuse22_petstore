package org.csu.petstorecms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("org.csu.petstorecms.DAO")
@SpringBootApplication
public class PetstoreCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetstoreCmsApplication.class, args);
    }

}
