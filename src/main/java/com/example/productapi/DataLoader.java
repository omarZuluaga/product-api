package com.example.productapi;

import com.example.productapi.model.Product;
import com.example.productapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements ApplicationRunner {

    private ProductRepository productRepository;

    @Autowired
    public DataLoader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        productRepository.save(new Product("Escoba", "Escoba para barrer", new BigDecimal(22.111), "Aseo"));
        productRepository.save(new Product("Trapero", "Trapero para limpiar", new BigDecimal(22.111), "Aseo"));
        productRepository.save(new Product("Fab", "Detergente", new BigDecimal(22.111), "Aseo"));
    }
}
