package tn.esprit.devops_project.services;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductSeviceImplTest {
    @InjectMocks
    ProductServiceImpl productService;
    @Mock
    ProductRepository productRepository;
    @Mock
    StockRepository stockRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testAddProduct() {
        Long idStock = 1L;
        Stock stock = new Stock();
        Product product = new Product();
        when(stockRepository.findById(idStock)).thenReturn(Optional.of(stock));
        when(productRepository.save(product)).thenReturn(product);
        Product result = productService.addProduct(product, idStock);
        verify(stockRepository).findById(idStock);
        verify(productRepository).save(product);
        assertEquals(product, result);
        System.out.println(product);
    }
    @Test
    public void testRetrieveProduct() {
        Long productId = 1L;
        Product expectedProduct = new Product();
        expectedProduct.setIdProduct(productId);
        expectedProduct.setTitle("Coca cola");
        when(productRepository.findById(productId)).thenReturn(Optional.of(expectedProduct));
        Product result = productService.retrieveProduct(productId);
        verify(productRepository).findById(productId);
        assertNotNull(result);
        assertEquals(expectedProduct, result);
    }

    @Test(expected = NullPointerException.class)
    public void testRetrieveProductProductNotFound() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        productService.retrieveProduct(productId);

    }
    @Test
    public void testRetrieveAllProduct() {
        Product product1 = new Product(1L, "Product 1", 10.0f, 100, ProductCategory.ELECTRONICS, new Stock());
        Product product2 = new Product(2L, "Product 2", 20.0f, 50, ProductCategory.CLOTHING, new Stock());
        List<Product> expectedProducts = Arrays.asList(product1, product2);
        when(productRepository.findAll()).thenReturn(expectedProducts);
        List<Product> result = productService.retreiveAllProduct();
        verify(productRepository).findAll();
        assertNotNull(result);
        assertEquals(expectedProducts, result);
    }
    @Test
    public void testRetrieveProductByCategory() {
        ProductCategory category = ProductCategory.ELECTRONICS;
        Product product1 = new Product(1L, "Product 1", 10.0f, 100, category, new Stock());
        Product product2 = new Product(2L, "Product 2", 20.0f, 50, category, new Stock());
        List<Product> expectedProducts = Arrays.asList(product1, product2);
        when(productRepository.findByCategory(category)).thenReturn(expectedProducts);
        List<Product> result = productService.retrieveProductByCategory(category);
        verify(productRepository).findByCategory(category);
        assertNotNull(result);
        assertEquals(expectedProducts, result);
    }
    @Test
    public void testDeleteProduct() {
        Long productId = 1L;
        productService.deleteProduct(productId);
        verify(productRepository).deleteById(productId);
    }

    @Test
    public void testRetrieveProductStock() {
        Long stockId = 1L;
        String title  = "Coca";
        Product product1 = new Product(1L, "Product 1", 10.0f, 100, ProductCategory.ELECTRONICS, new Stock(stockId,title,null));
        Product product2 = new Product(2L, "Product 2", 20.0f, 50, ProductCategory.CLOTHING, new Stock(stockId,title,null));
        List<Product> expectedProducts = Arrays.asList(product1, product2);
        when(productRepository.findByStockIdStock(stockId)).thenReturn(expectedProducts);
        List<Product> result = productService.retreiveProductStock(stockId);
        verify(productRepository).findByStockIdStock(stockId);
        assertNotNull(result);
        assertEquals(expectedProducts, result);
    }


}


