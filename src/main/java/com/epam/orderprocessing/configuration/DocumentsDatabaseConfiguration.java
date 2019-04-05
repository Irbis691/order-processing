package com.epam.orderprocessing.configuration;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "documentsEntityManagerFactory",
        transactionManagerRef = "documentsTransactionManager",
        basePackages = {"com.epam.orderprocessing.document.repository"}
)
public class DocumentsDatabaseConfiguration {
    @Bean(name = "documentsDataSource")
    @ConfigurationProperties(prefix = "orderprocessing.datasources.documents")
    public DataSource documentsDataSource(DatabaseServiceCredentials serviceCredentials) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(serviceCredentials.jdbcUrl("documents-mysql"));
        HikariConfig config = new HikariConfig();
        config.setDataSource(dataSource);
        return new HikariDataSource(config);
    }

    @Bean(name = "documentsEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    documentsEntityManagerFactory(HibernateJpaVendorAdapter adapter, @Qualifier("documentsDataSource") DataSource documentsDataSource) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("configured", "manually");
        properties.put("hibernate.transaction.jta.platform", NoJtaPlatform.INSTANCE);
        return new EntityManagerFactoryBuilder(adapter, properties, null)
                .dataSource(documentsDataSource)
                .packages("com.epam.orderprocessing.document.domain")
                .persistenceUnit("documents")
                .build();
    }

    @Bean(name = "documentsTransactionManager")
    public PlatformTransactionManager documentsTransactionManager(@Qualifier("documentsEntityManagerFactory") EntityManagerFactory documentsEntityManagerFactory) {
        return new JpaTransactionManager(documentsEntityManagerFactory);
    }
}