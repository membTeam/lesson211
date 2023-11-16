package beanComp.config;

import beanComp.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import beanComp.models.Product;

@Component
public class ConfigLoadData implements CommandLineRunner {

    private ProductRepository repo;

    public ConfigLoadData(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) {
        try {

            repo.deleteAll();

            repo.save(new Product(null, 10, "Гвоздики"));
            repo.save(new Product(null, 20, "Розы"));
            repo.save(new Product(null, 15, "Пионы"));
            repo.save(new Product(null, 10, "Герберы"));
            repo.save(new Product(null, 5, "Гибсофилы"));

            System.out.println("Заполнены начальные данные");

        } catch (Exception ex) {
            System.out.println("err: " + ex.getMessage());
        }
    }
}

