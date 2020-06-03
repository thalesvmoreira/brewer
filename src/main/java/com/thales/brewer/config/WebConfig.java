package com.thales.brewer.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.cache.Caching;


@Configuration
@EnableSpringDataWebSupport
@EnableCaching
@EnableAsync
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public CacheManager cacheManager() throws Exception{
        return new JCacheCacheManager(Caching.getCachingProvider().getCacheManager(
                getClass().getResource("/env/ehcache.xml").toURI(),
                getClass().getClassLoader()
        ));
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource bundle = new ReloadableResourceBundleMessageSource();
        bundle.setBasename("classpath:/messages");
        bundle.setDefaultEncoding("UTF-8"); // http://www.utf8-chartable.de/
        return bundle;
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setValidationMessageSource(messageSource());

        return validatorFactoryBean;
    }

    @Override
    public Validator getValidator() {
        return validator();
    }
}
