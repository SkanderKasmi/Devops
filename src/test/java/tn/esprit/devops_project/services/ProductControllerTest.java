package tn.esprit.devops_project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.devops_project.controllers.ProductController;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.services.Iservices.IProductService;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private IProductService productService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testAddProduct() throws Exception {
        Product product = new Product();
        product.setIdProduct(1L);
        product.setTitle("Test Product");
        Mockito.when(productService.addProduct(Mockito.any(Product.class), Mockito.anyLong()))
                .thenReturn(product);
        mockMvc.perform(MockMvcRequestBuilders.post("/product/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Product\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testRetrieveProduct() throws Exception {
        Product product = new Product();
        product.setIdProduct(1L);
        product.setTitle("Test Product");
        Mockito.when(productService.retrieveProduct(Mockito.anyLong()))
                .thenReturn(product);
        mockMvc.perform(MockMvcRequestBuilders.get("/product/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testRetrieveAllProduct() throws Exception {
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product();
        product1.setIdProduct(1L);
        product1.setTitle("Product 1");
        Product product2 = new Product();
        product2.setIdProduct(2L);
        product2.setTitle("Product 2");
        productList.add(product1);
        productList.add(product2);
        Mockito.when(productService.retreiveAllProduct())
                .thenReturn(productList);
        mockMvc.perform(MockMvcRequestBuilders.get("/product"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testRetrieveProductStock() throws Exception {
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product();
        product1.setIdProduct(1L);
        product1.setTitle("Product 1");
        Product product2 = new Product();
        product2.setIdProduct(2L);
        product2.setTitle("Product 2");
        productList.add(product1);
        productList.add(product2);
        Mockito.when(productService.retreiveProductStock(Mockito.anyLong()))
                .thenReturn(productList);
        mockMvc.perform(MockMvcRequestBuilders.get("/product/stock/123"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }



    @Test
    public void testDeleteProduct() throws Exception {
        Mockito.doNothing().when(productService).deleteProduct(Mockito.anyLong());
        mockMvc.perform(MockMvcRequestBuilders.delete("/product/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}
