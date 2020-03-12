package com.csdn.label;

import com.csdn.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LabelApplication1 {

    public static void main(String[] args) {
        SpringApplication.run(LabelApplication1.class,args);
    }

    @Bean
    public IdWorker idWorker()
    {
        return new IdWorker(1,1);
    }
}
