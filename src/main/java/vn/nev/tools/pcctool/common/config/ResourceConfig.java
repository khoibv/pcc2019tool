package vn.nev.tools.pcctool.common.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class ResourceConfig {
    /**
     * MessageDto resource bean
     * Load from messages.properties file
     *
     * @return
     */
    @Bean(name = "messageSource")
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource bean = new ReloadableResourceBundleMessageSource();
        bean.addBasenames("classpath:messages", "classpath:labels");
        return bean;
    }

    /**
     * Label resource bean
     * Load from labels.properties file
     *
     * @return
     */
    @Bean(name = "labelSource")
    public MessageSource labelSource() {
        ReloadableResourceBundleMessageSource bean = new ReloadableResourceBundleMessageSource();
        bean.addBasenames("classpath:labels");
        return bean;
    }

    @Bean(name = "validationMessages")
    public MessageSource validationMessagesSource() {
        ReloadableResourceBundleMessageSource bean = new ReloadableResourceBundleMessageSource();
        bean.addBasenames("classpath:ValidationMessages", "classpath:messages");
        bean.setDefaultEncoding("UTF-8");
        return bean;
    }

}
