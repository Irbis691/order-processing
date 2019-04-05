package com.epam.orderprocessing.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class DatabaseConfiguration {

    @Bean
    DatabaseServiceCredentials serviceCredentials(@Value("{\"p-mysql\": [{\"credentials\": {\"jdbcUrl\": \"jdbc:mysql://127.0.0.1:3306/documents?user=root&password=root\"}, \"name\": \"documents-mysql\"}, {\"credentials\": {\"jdbcUrl\": \"jdbc:mysql://127.0.0.1:3306/orders?user=root&password=root\"}, \"name\": \"orders-mysql\"}]}") String vcapServices) {
        return new DatabaseServiceCredentials(vcapServices);
    }

    @Bean
    HibernateJpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.MYSQL);
        jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");
        jpaVendorAdapter.setGenerateDdl(true);
        return jpaVendorAdapter;
    }
}