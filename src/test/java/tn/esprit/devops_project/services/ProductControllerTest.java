package tn.esprit.devops_project.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;


import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.test.web.servlet.MockMvc;

import tn.esprit.devops_project.controllers.ProductController;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.ProductDTO;
import tn.esprit.devops_project.services.Iservices.IProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRetrieveProduct() throws Exception {
        Long productId = 1L;
        Product product = new Product();
        product.setIdProduct(productId);
        product.setTitle("Sample Product");
        product.setPrice(20.0f);
        product.setQuantity(10);
        product.setCategory(ProductCategory.ELECTRONICS);

        when(productService.retrieveProduct(productId)).thenReturn(product);

        mockMvc.perform(get("/product/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(convertToProductDTO(product))));
    }

    @Test
    public void testAddProduct() throws Exception {
        Long stockId = 1L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setTitle("New Product 1 ");
        productDTO.setPrice(30.0f);
        productDTO.setQuantity(5);
        productDTO.setCategory(ProductCategory.BOOKS);
        Product product = convertToProduct(productDTO);
        when(productService.addProduct(any(Product.class), any(Long.class))).thenReturn(product);
        mockMvc.perform(post("/product/{idStock}", stockId)
                        .content(objectMapper.writeValueAsString(productDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(convertToProductDTO(product))));
    }

    @Test
    public void testRetrieveAllProduct() throws Exception {
        List<Product> productList = new ArrayList<>();
        when(productService.retreiveAllProduct()).thenReturn(productList);
        mockMvc.perform(get("/product"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productList.stream()
                        .map(this::convertToProductDTO)
                        .collect(Collectors.toList()))));
    }
    @Test
    public void testRetrieveProductStock() throws Exception {
        Long stockId = 1L;
        List<Product> products = new ArrayList<>();
        when(productService.retreiveProductStock(stockId)).thenReturn(products);
        mockMvc.perform(get("/product/stock/{id}", stockId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(products)));
    }
    @Test
    public void testRetrieveProductByCategory() throws Exception {
        ProductCategory category = ProductCategory.ELECTRONICS;
        List<Product> products = new ArrayList<>();
        when(productService.retrieveProductByCategory(category)).thenReturn(products);
        mockMvc.perform(get("/productCategoy/{category}", category))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(products)));
    }
    @Test
    public void testDeleteProduct() throws Exception {
        Long productId = 1L;

        mockMvc.perform(delete("/product/{id}", productId))
                .andExpect(status().isOk());
        verify(productService, times(1)).deleteProduct(productId);
    }



    private ProductDTO convertToProductDTO(Product product) {
        return new ProductDTO(
                product.getIdProduct(),
                product.getTitle(),
                product.getPrice(),
                product.getQuantity(),
                product.getCategory()
        );
    }

    private Product convertToProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setIdProduct(productDTO.getIdProduct());
        product.setTitle(productDTO.getTitle());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setCategory(productDTO.getCategory());
        return product;
    }
}
