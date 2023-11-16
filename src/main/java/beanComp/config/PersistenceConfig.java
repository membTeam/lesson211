package beanComp.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;

import beanComp.models.Product;

@Configuration
public class PersistenceConfig {
    @Bean
    public ApplicationListener<BeforeSaveEvent> idGenerator() {
        return event -> {
            var entity = event.getEntity();

            var objEntity = ((Product) entity);
            if (objEntity.getId() == null) {
                objEntity.setId(InitIdProduct.initId());
            }
        };
    }

}
