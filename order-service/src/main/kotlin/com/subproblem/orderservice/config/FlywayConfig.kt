package com.subproblem.orderservice.config

import org.flywaydb.core.Flyway
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource


@Configuration
class FlywayConfig {

    @Bean(initMethod = "migrate")
    fun flyway(@Qualifier("orderDataSource") dataSource: DataSource): Flyway {
        return Flyway.configure()
            .dataSource(dataSource)
            .locations("classpath:db/migration/order")
            .load()
    }
}