package com.company.sample.jta.datasource.orders;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.company.sample.jta.AtomikosServerPlatform;
import io.jmix.autoconfigure.data.JmixLiquibase;
import io.jmix.data.impl.liquibase.LiquibaseChangeLogProcessor;
import liquibase.integration.spring.SpringLiquibase;
import org.postgresql.xa.PGXADataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@DependsOn("transactionManager")
public class OrderDatasourceConfiguration {

    @Autowired
    OrdersDatasourceProperties dsConfig;

    @Bean
    @Qualifier("orders")
    DataSource ordersDataSource() {
        PGXADataSource ds = new PGXADataSource();
        ds.setURL(dsConfig.getJdbcUrl());
        ds.setUser(dsConfig.getUsername());
        ds.setPassword(dsConfig.getPassword());
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setUniqueResourceName("cubaXADs/orders");
        atomikosDataSourceBean.setXaDataSource(ds);
        atomikosDataSourceBean.setMaxPoolSize(100);
        return atomikosDataSourceBean;
    }

    @Bean
    @DependsOn({"transactionManager", "ordersDataSource"})
    public LocalContainerEntityManagerFactoryBean ordersEntityManagerFactory(@Qualifier("orders") DataSource ordersDataSource,
                                                                JpaVendorAdapter jpaVendorAdapter,
                                                                JpaDialect dialect) throws Throwable {

        Map<String, Object> properties = new HashMap<>();
        properties.put("eclipselink.target-server", AtomikosServerPlatform.class.getName());
        properties.put("javax.persistence.transactionType", "JTA");

        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setJpaDialect(dialect);
        entityManager.setJtaDataSource(ordersDataSource);
        entityManager.setJpaVendorAdapter(jpaVendorAdapter);
        entityManager.setPersistenceUnitName("orders");
        entityManager.setPackagesToScan("com.company.sample.entity.orders", "io.jmix");
        entityManager.setJpaPropertyMap(properties);
        return entityManager;
    }

    @Bean
    public SpringLiquibase ordersLiquibase(LiquibaseChangeLogProcessor processor) {
        return JmixLiquibase.create(ordersDataSource(), new LiquibaseProperties(), processor, "orders");
    }


}
