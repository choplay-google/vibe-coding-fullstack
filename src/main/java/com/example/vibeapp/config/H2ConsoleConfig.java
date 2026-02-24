package com.example.vibeapp.config;

import org.h2.server.web.JakartaWebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Boot 4.0.1 (Jakarta EE) 환경에서 H2 콘솔을 수동으로 설정합니다.
 */
@Configuration
public class H2ConsoleConfig {

    @Bean
    public ServletRegistrationBean<JakartaWebServlet> h2Console() {
        // JakartaWebServlet을 사용하여 /h2-console 경로에 매핑
        ServletRegistrationBean<JakartaWebServlet> registrationBean = 
            new ServletRegistrationBean<>(new JakartaWebServlet(), "/h2-console/*");
        
        // 원격 접속 허용 등 필요한 파라미터 설정 (선택 사항)
        registrationBean.addInitParameter("webAllowOthers", "true");
        
        return registrationBean;
    }
}
