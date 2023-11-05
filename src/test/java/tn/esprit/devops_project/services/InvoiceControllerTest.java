package tn.esprit.devops_project.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tn.esprit.devops_project.controllers.InvoiceController;
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.repositories.InvoiceRepository;
import tn.esprit.devops_project.services.Iservices.IInvoiceService;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(InvoiceController.class)
@AutoConfigureMockMvc
public class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IInvoiceService invoiceService;


    @Test
    public void testGetInvoices() throws Exception {

        List<Invoice> invoices = new ArrayList<>();
        invoices.add(new Invoice(/* données factices */));

        Mockito.when(invoiceService.retrieveAllInvoices()).thenReturn(invoices);

        mockMvc.perform(MockMvcRequestBuilders.get("/invoice"))
                .andExpect(status().isOk());

    }

    @Test
    public void testRetrieveInvoice() throws Exception {

        Invoice invoice = new Invoice();
        Long invoiceId = 1L;

        Mockito.when(invoiceService.retrieveInvoice(invoiceId)).thenReturn(invoice);

        mockMvc.perform(MockMvcRequestBuilders.get("/invoice/" + invoiceId))
                .andExpect(status().isOk());

    }

    @Test
    public void testCancelInvoice() throws Exception {
        Long invoiceId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.put("/invoice/" + invoiceId))
                .andExpect(status().isOk());

        Mockito.verify(invoiceService, Mockito.times(1)).cancelInvoice(invoiceId);
    }

    @Test
    public void testGetInvoicesBySupplier() throws Exception {
        Long supplierId = 1L;

        List<Invoice> invoices = new ArrayList<>();
        invoices.add(new Invoice(/* données factices */));

        Mockito.when(invoiceService.getInvoicesBySupplier(supplierId)).thenReturn(invoices);

        mockMvc.perform(MockMvcRequestBuilders.get("/invoice/supplier/" + supplierId))
                .andExpect(status().isOk());

    }

    @Test
    public void testAssignOperatorToInvoice() throws Exception {
        Long idOperator = 1L;
        Long idInvoice = 2L;

        mockMvc.perform(MockMvcRequestBuilders.put("/invoice/operator/" + idOperator + "/" + idInvoice))
                .andExpect(status().isOk());

        Mockito.verify(invoiceService, Mockito.times(1)).assignOperatorToInvoice(idOperator, idInvoice);
    }
//    @Test
//    public void testGetTotalAmountInvoiceBetweenDates() throws Exception {
//        String startDate = "2023-01-01";
//        String endDate = "2023-12-31";
//        float expectedTotalAmount = 1000.0F;  // Replace with your expected total amount
//        Mockito.when(invoiceService.getTotalAmountInvoiceBetweenDates(Mockito.any(Date.class), Mockito.any(Date.class)))
//                .thenReturn(expectedTotalAmount);
//        mockMvc.perform(MockMvcRequestBuilders.get("/invoice/price/" + startDate + "/" + endDate))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string(String.valueOf(expectedTotalAmount)));
//    }


}
