package uk.ac.herc.bcra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer ;

import uk.ac.herc.bcra.config.ratelimiting.RateLimitInterceptor;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer  {
    
    private RateLimitInterceptor rateLimitInterceptor;

    public WebMVCConfig(RateLimitInterceptor rateLimitInterceptor) {
        this.rateLimitInterceptor = rateLimitInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimitInterceptor);
    }
}
