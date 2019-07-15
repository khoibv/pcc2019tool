package vn.nev.tools.pcctool.common.config;

import java.util.Collections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class TemplateConfig implements ApplicationContextAware {


  @Autowired
  private SpringTemplateEngine templateEngine;

  private ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @Bean(name = "codeGenerator")
  public TemplateEngine codeGeneratorTemplateEngine() {
    final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    // Resolver for TEXT files
    templateEngine.addTemplateResolver(codeGeneratorTemplateResolver());

    return templateEngine;
  }

  private ITemplateResolver codeGeneratorTemplateResolver() {
    final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setOrder(1);
    templateResolver.setResolvablePatterns(Collections.singleton("files/*"));
    templateResolver.setPrefix("/templates/");
    templateResolver.setSuffix(".java");
    templateResolver.setTemplateMode(TemplateMode.TEXT);
    templateResolver.setCharacterEncoding("UTF-8");
    templateResolver.setCacheable(false);

    return templateResolver;
  }

}
