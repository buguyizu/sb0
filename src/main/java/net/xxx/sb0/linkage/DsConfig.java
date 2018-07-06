package net.xxx.sb0.linkage;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import net.xxx.sb0.linkage.ds.ConTarget;
import net.xxx.sb0.linkage.ds.Ds2;
import net.xxx.sb0.linkage.ds.DynamicRoutingDsResolver;

@SpringBootApplication
public class DsConfig {

	@Autowired
	private Ds2 ds2;

	@Autowired
	private Environment env;

	@Bean()
	public DynamicRoutingDsResolver dataSource() {

		Map<Object, Object> dataSources = new HashMap<Object, Object>();

		dataSources.put(ConTarget.Default, ds());

		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(ds2.getDriverClassName());
		ds.setUrl(ds2.getUrl());
		ds.setUsername(ds2.getUsername());
		ds.setPassword(ds2.getPassword());
		dataSources.put(ConTarget.Third, ds);

		DynamicRoutingDsResolver resolver = new DynamicRoutingDsResolver();
		resolver.setTargetDataSources(dataSources);

		return resolver;
	}

	private DriverManagerDataSource ds() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(env.getProperty("spring.datasource.url"));
		dataSource.setUsername(env.getProperty("spring.datasource.username"));
		dataSource.setPassword(env.getProperty("spring.datasource.password"));
		return dataSource;
	}
}
