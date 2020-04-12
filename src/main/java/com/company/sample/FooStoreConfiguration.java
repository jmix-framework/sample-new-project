package com.company.sample;

import io.jmix.autoconfigure.data.JmixLiquibase;
import io.jmix.data.impl.JmixEntityManagerFactoryBean;
import io.jmix.data.impl.JmixTransactionManager;
import io.jmix.data.impl.PersistenceConfigProcessor;
import io.jmix.data.impl.liquibase.LiquibaseChangeLogProcessor;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class FooStoreConfiguration {

    @Bean
    @ConfigurationProperties(prefix="foo.datasource")
    DataSource fooDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    LocalContainerEntityManagerFactoryBean fooEntityManagerFactory(PersistenceConfigProcessor processor, JpaVendorAdapter jpaVendorAdapter) {
        return new JmixEntityManagerFactoryBean("foo", fooDataSource(), processor, jpaVendorAdapter);
    }

    @Bean
    JpaTransactionManager fooTransactionManager(@Qualifier("fooEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JmixTransactionManager("foo", entityManagerFactory);
    }

    @Bean
    public SpringLiquibase fooLiquibase(LiquibaseChangeLogProcessor processor) {
        return JmixLiquibase.create(fooDataSource(), new LiquibaseProperties(), processor, "foo");
    }
}
