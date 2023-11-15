package beanComp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Random;

@Configuration
public class RandomConfiguration {
    @Bean
    public Random random() {
        return new Random(256);
    }

    @Bean
    @Primary
    public Random defaultRandom() {
        return new Random();
    }
}
