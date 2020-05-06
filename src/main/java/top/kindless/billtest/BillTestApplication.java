package top.kindless.billtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableCaching
@EnableJpaAuditing
public class BillTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillTestApplication.class, args);
    }

}
