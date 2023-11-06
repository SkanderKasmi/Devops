package tn.esprit.devops_project.services;

import org.junit.jupiter.api.Test;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.ProductDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ProductEntityTest {

    @Test
    public void testEntityToDTO() {
        Product product = new Product();
        product.setIdProduct(1L);
        product.setTitle("Sample Product");
        product.setPrice(20.0f);
        product.setQuantity(10);
        product.setCategory(ProductCategory.ELECTRONICS);

        ProductDTO productDTO = new ProductDTO(product.getIdProduct(), product.getTitle(), product.getPrice(), product.getQuantity(), product.getCategory());

        assertEquals(product.getIdProduct(), productDTO.getIdProduct());
        assertEquals(product.getTitle(), productDTO.getTitle());
        assertEquals(product.getPrice(), productDTO.getPrice(), 0.001);
        assertEquals(product.getQuantity(), productDTO.getQuantity());
        assertEquals(product.getCategory(), productDTO.getCategory());

    }


}
