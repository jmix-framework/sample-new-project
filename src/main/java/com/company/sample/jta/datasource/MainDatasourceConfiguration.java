package com.company.sample.jta.datasource;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.company.sample.jta.AtomikosServerPlatform;
import org.postgresql.xa.PGXADataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@DependsOn("transactionManager")
public class MainDatasourceConfiguration {

    @Bean(name = "dataSource")
    @Primary
    DataSource dataSource(MainDatasourceProperties dsConfig) {
        PGXADataSource ds = new PGXADataSource();
        ds.setURL(dsConfig.getJdbcUrl());
        ds.setUser(dsConfig.getUsername());
        ds.setPassword(dsConfig.getPassword());
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setUniqueResourceName("cubaXADs/main");
        atomikosDataSourceBean.setXaDataSource(ds);
        atomikosDataSourceBean.setMaxPoolSize(100);
        return atomikosDataSourceBean;
    }

    @Bean(name = "entityManagerFactory")
    @DependsOn({"transactionManager", "dataSource"})
    public LocalContainerEntityManagerFactoryBean entityManager(DataSource dataSource,
                                                                JpaVendorAdapter jpaVendorAdapter,
                                                                JpaDialect dialect) throws Throwable {

        Map<String, Object> properties = new HashMap<>();
        properties.put("eclipselink.target-server", AtomikosServerPlatform.class.getName());
        properties.put("javax.persistence.transactionType", "JTA");

        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setJpaDialect(dialect);
        entityManager.setJtaDataSource(dataSource);
        entityManager.setJpaVendorAdapter(jpaVendorAdapter);
        entityManager.setPersistenceUnitName("main");
        entityManager.setPackagesToScan("com.company.sample.entity", "io.jmix");
        entityManager.setJpaPropertyMap(properties);
        return entityManager;
    }


}
