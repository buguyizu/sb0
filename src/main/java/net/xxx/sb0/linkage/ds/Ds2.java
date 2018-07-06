package net.xxx.sb0.linkage.ds;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:datasource.properties")
public class Ds2 extends DsProperties {

  @Override
  @Value("${spring.datasource.ds2.url}")
  public void setUrl(String url) {
    super.setUrl(url);
  }

  @Override
  @Value("${spring.datasource.ds2.username}")
  public void setUsername(String username) {
    super.setUsername(username);
  }

  @Override
  @Value("${spring.datasource.ds2.password}")
  public void setPassword(String password) {
    super.setPassword(password);
  }

  @Override
  @Value("${spring.datasource.ds2.driver-class-name}")
  public void setDriverClassName(String driverClassName) {
    super.setDriverClassName(driverClassName);
  }
}
