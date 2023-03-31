package com.example.productapi.service;

import com.example.productapi.exception.ResourceNotFoundException;
import com.example.productapi.model.Product;
import com.example.productapi.repository.ProductRepository;
import com.example.productapi.service.impl.ProductServiceImp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImpTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImp productServiceImp;

    private Product product1;
    private Product product2;
    private List<Product> productList;

    @Before
    public void setUp() {
        product1 = new Product("Product1", "Product 1 with 1 info", new BigDecimal("20.000"), "CategoryTest");
        product2 = new Product("Product1", "Product 2 with 2 info", new BigDecimal("20.000"), "CategoryTest");
        productList = Arrays.asList(product1, product2);
    }

    @Test
    public void testGetProducts() {
        Mockito.when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productServiceImp.getProducts();

        Assert.assertEquals(productList.size(), result.size());
        Assert.assertEquals(productList.get(0), result.get(0));
        Assert.assertEquals(productList.get(1), result.get(1));

        Mockito.verify(productRepository, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testGetProductById() {
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product1));

        Product result = productServiceImp.getProductById(1L);

        Assert.assertEquals(product1, result);

        Mockito.verify(productRepository, Mockito.times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testGetProductByIdNotFound() {
        Mockito.when(productRepository.findById(3L)).thenReturn(Optional.empty());

        try {
            productServiceImp.getProductById(3L);
            Assert.fail("Expected ResourceNotFoundException");
        } catch (ResourceNotFoundException e) {
            Assert.assertEquals("product not found", e.getMessage());
        }

        Mockito.verify(productRepository, Mockito.times(1)).findById(3L);
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testSaveProduct() {
        Mockito.when(productRepository.save(product1)).thenReturn(product1);

        Product result = productServiceImp.saveProduct(product1);

        Assert.assertEquals(product1, result);

        Mockito.verify(productRepository, Mockito.times(1)).save(product1);
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testUpdateProduct() {
        Mockito.when(productRepository.save(product1)).thenReturn(product1);

        Product result = productServiceImp.updateProduct(product1);

        Assert.assertEquals(product1, result);

        Mockito.verify(productRepository, Mockito.times(1)).save(product1);
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testDeleteProduct() {
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product1));

        productServiceImp.deleteProduct(1L);

        Mockito.verify(productRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(productRepository, Mockito.times(1)).delete(product1);
        Mockito.verifyNoMoreInteractions(productRepository);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteProductNotFound() {
        Mockito.when(productRepository.findById(3L)).thenReturn(Optional.empty());

        productServiceImp.deleteProduct(3L);

        Mockito.verify(productRepository, Mockito.times(1)).findById(3L);
        Mockito.verifyNoMoreInteractions(productRepository);
    }
}
