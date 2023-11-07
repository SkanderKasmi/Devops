package tn.esprit.devops_project.services;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.repositories.InvoiceRepository;
import tn.esprit.devops_project.repositories.OperatorRepository;
import tn.esprit.devops_project.repositories.SupplierRepository;
import tn.esprit.devops_project.services.Iservices.IInvoiceService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(OrderAnnotation.class)
@Transactional
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@ActiveProfiles("test")
public class InvoiceServiceImplJUnitTest {

    @Autowired
    IInvoiceService invoiceService;
    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    OperatorRepository operatorRepository;
    @Autowired
    SupplierRepository supplierRepository;

    @Test
    @Order(2)
    @DatabaseSetup("/data-set/invoice-data.xml")
    public void testRetrieveAllInvoices() {
        List<Invoice> listProduits = invoiceService.retrieveAllInvoices();
        Assertions.assertEquals(listProduits.size(), listProduits.size());
    }

    @Test
    @Order(1)
    @DatabaseSetup("/data-set/invoice-data.xml")
    public void testGetTotalAmountInvoiceBetweenDates() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = dateFormat.parse("30/11/2024");
        Date endDate = dateFormat.parse("25/12/2024");


        Invoice invoice1 = new Invoice();
        invoice1.setDateCreationInvoice(startDate);
        invoice1.setDateLastModificationInvoice(endDate);
        invoice1.setAmountInvoice(100.0f);
        invoice1.setArchived(false);
        invoiceRepository.save(invoice1);

        Invoice invoice2 = new Invoice();
        invoice2.setDateCreationInvoice(startDate);
        invoice2.setDateLastModificationInvoice(endDate);
        invoice2.setAmountInvoice(200.0f);
        invoice2.setArchived(false);
        invoiceRepository.save(invoice2);


        float totalAmount = invoiceService.getTotalAmountInvoiceBetweenDates(startDate,endDate);
        assertEquals(300.0f, totalAmount);
    }

 

    @Test
    @Order(4)
    @DatabaseSetup("/data-set/invoice-data.xml")
    public void testCancelInvoice() throws ParseException  {
        Invoice invoice = new Invoice();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = dateFormat.parse("30/09/2020");
        Date date2 = dateFormat.parse("05/12/2022");
        invoice.setDateCreationInvoice(date1);
        invoice.setDateLastModificationInvoice(date2);
        invoice.setAmountInvoice(1.5f);
        invoice.setArchived(false);
        invoiceRepository.save(invoice);

        invoiceService.cancelInvoice(invoice.getIdInvoice());
        assertNotNull(invoice.getIdInvoice());
    }

    @Test
    @Order(5)
    @DatabaseSetup("/data-set/invoice-data.xml")
    public void delete() {
        operatorRepository.deleteAll();
        invoiceRepository.deleteAll();
    }

}