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
import org.springframework.context.annotation.Primary;
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
        entityManagerFactoryRef = "ordersEntityManagerFactory",
        transactionManagerRef = "ordersTransactionManager",
        basePackages = {"com.epam.orderprocessing.order.repository"}
)
public class OrdersDatabaseConfiguration {
    @Primary
    @Bean(name = "ordersDataSource")
    @ConfigurationProperties(prefix = "orderprocessing.datasources.orders")
    public DataSource orderDataSource(DatabaseServiceCredentials serviceCredentials) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(serviceCredentials.jdbcUrl("orders-mysql"));
        HikariConfig config = new HikariConfig();
        config.setDataSource(dataSource);
        return new HikariDataSource(config);
    }

    @Primary
    @Bean(name = "ordersEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    ordersEntityManagerFactory(HibernateJpaVendorAdapter adapter, @Qualifier("ordersDataSource") DataSource orderDataSource) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("configured", "manually");
        properties.put("hibernate.transaction.jta.platform", NoJtaPlatform.INSTANCE);
        return new EntityManagerFactoryBuilder(adapter, properties, null)
                .dataSource(orderDataSource)
                .packages("com.epam.orderprocessing.order.domain")
                .persistenceUnit("orders")
                .build();
    }

    @Primary
    @Bean(name = "ordersTransactionManager")
    public PlatformTransactionManager ordersTransactionManager(@Qualifier("ordersEntityManagerFactory") EntityManagerFactory ordersTransactionManagerFactory) {
        return new JpaTransactionManager(ordersTransactionManagerFactory);
    }
}