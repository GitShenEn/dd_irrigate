package cn.dreamdeck;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.dreamdeck.mapper")
public class DdMarathonApplication {

    public static void main(String[] args) {
        SpringApplication.run(DdMarathonApplication.class, args);
    }

}
