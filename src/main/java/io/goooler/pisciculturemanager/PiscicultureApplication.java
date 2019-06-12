package io.goooler.pisciculturemanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "io.goooler.pisciculturemanager.mapper")
public class PiscicultureApplication {

    public static void main(String[] args) {
        SpringApplication.run(PiscicultureApplication.class, args);
    }

}
