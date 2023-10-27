package com.kpi.springboot_laba_2.config;

import com.kpi.springboot_laba_2.util.CategoryDB;
import com.kpi.springboot_laba_2.util.ProductDB;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ProductDB productDB(){
        return new ProductDB();
    }

    @Bean
    public CategoryDB categoryDB(){
        return new CategoryDB();
    }

}
