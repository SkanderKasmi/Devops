package tn.esprit.devops_project.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tn.esprit.devops_project.entities.Invoice;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class InvoiceEntityTest {

    private Invoice invoice;

    @BeforeEach
    public void setUp() {
        invoice = new Invoice();
    }

    @Test
    public void testGettersAndSetters() {
        // Set values using setters
        invoice.setIdInvoice(1L);
        invoice.setAmountDiscount(10.0f);
        invoice.setAmountInvoice(100.0f);
        invoice.setDateCreationInvoice(new Date());
        invoice.setDateLastModificationInvoice(new Date());
        invoice.setArchived(false);

        // Verify values using getters
        assertEquals(1L, invoice.getIdInvoice());
        assertEquals(10.0f, invoice.getAmountDiscount());
        assertEquals(100.0f, invoice.getAmountInvoice());
        assertNotNull(invoice.getDateCreationInvoice());
        assertNotNull(invoice.getDateLastModificationInvoice());
        assertFalse(invoice.isArchived());
    }




}
