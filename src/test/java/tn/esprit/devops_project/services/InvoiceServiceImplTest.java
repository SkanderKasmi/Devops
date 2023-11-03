package tn.esprit.devops_project.services;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;



import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.entities.SupplierCategory;
import tn.esprit.devops_project.repositories.InvoiceRepository;
import tn.esprit.devops_project.repositories.SupplierRepository;

import java.util.*;

public class InvoiceServiceImplTest {
    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    @Mock
    private InvoiceRepository invoiceRepository;
    @Mock
    private SupplierRepository supplierRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveAllInvoices() {
        Invoice invoice1 = new Invoice(1L, 10.0f, 100.0f, new Date(), new Date(), true, new HashSet<>(), new Supplier());
        Invoice invoice2 = new Invoice(2L, 20.0f, 200.0f, new Date(), new Date(), false, new HashSet<>(), new Supplier());
        List<Invoice> expectedInvoices = Arrays.asList(invoice1, invoice2);
        when(invoiceRepository.findAll()).thenReturn(expectedInvoices);
        List<Invoice> result = invoiceService.retrieveAllInvoices();
        verify(invoiceRepository).findAll();
        assertNotNull(result);
        assertEquals(expectedInvoices, result);
    }
    @Test
    public void testCancelInvoice() {
        Long invoiceId = 1L;
        Invoice invoice = new Invoice(invoiceId, 10.0f, 100.0f, new Date(), new Date(), false, new HashSet<>(), new Supplier());
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));
        invoiceService.cancelInvoice(invoiceId);
        verify(invoiceRepository).findById(invoiceId);
        assertTrue(invoice.getArchived());
        verify(invoiceRepository).updateInvoice(invoiceId);
    }
    @Test
    public void testRetrieveInvoice() {
        Long invoiceId = 1L;
        Invoice expectedInvoice = new Invoice(invoiceId, 10.0f, 100.0f, new Date(), new Date(), false, new HashSet<>(), new Supplier());
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(expectedInvoice));
        Invoice result = invoiceService.retrieveInvoice(invoiceId);
        verify(invoiceRepository).findById(invoiceId);
        assertNotNull(result);
        assertEquals(expectedInvoice, result);
    }
  
}
