package tn.esprit.devops_project.services;

import org.junit.jupiter.api.Test;
import tn.esprit.devops_project.entities.Product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ProductEntityTest {

    @Test
    public void testEquals() {
        Product product1 = new Product();
        product1.setIdProduct(1L);
        Product product2 = new Product();
        product2.setIdProduct(1L);
        assertEquals(product1, product2);
    }

    @Test
    public void testNotEquals() {
        Product product1 = new Product();
        product1.setIdProduct(1L);
        Product product2 = new Product();
        product2.setIdProduct(2L);
        assertNotEquals(product1, product2);
    }


}
