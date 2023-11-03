package tn.esprit.devops_project.services;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.entities.Supplier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@ActiveProfiles("test")
public class SupplierServiceImplTest {
    @Autowired
    SupplierServiceImpl supplierService;
    @Test
    @DatabaseSetup("/data-set/supplier-data.xml")
    void addSupplier() {
        final Supplier supplier = new Supplier();
        supplier.setLabel("skander");
        this.supplierService.addSupplier(supplier);
        assertEquals(this.supplierService.retrieveAllSuppliers().size(),2);
        assertEquals(this.supplierService.retrieveSupplier(2L).getLabel(),"skander");
    }

    @Test
    @DatabaseSetup("/data-set/supplier-data.xml")
    void retrieveSupplier() {
        final Supplier supplier = this.supplierService.retrieveSupplier(1L);
        assertEquals(1L, supplier.getIdSupplier());
    }

    @Test
    @DatabaseSetup("/data-set/supplier-data.xml")
    void retrieveAllSupplier() {
        final List<Supplier> allSuppliers = this.supplierService.retrieveAllSuppliers();
        assertEquals(allSuppliers.size(), 1);

    }
    @Test
    @DatabaseSetup("/data-set/supplier-data.xml")
    void  updatesupplier (){
        final  Supplier supplier = this.supplierService.retrieveSupplier(1L);
        assertEquals(1L,supplier.getIdSupplier());
        supplier.setLabel("Germany");
        this.supplierService.addSupplier(supplier);
        assertEquals(this.supplierService.retrieveAllSuppliers().size(),1);
        assertEquals(this.supplierService.retrieveSupplier(1L).getLabel(),"Germany");

    }
    @Test
    @DatabaseSetup("/data-set/supplier-data.xml")
    void deletesupplier(){
        this.supplierService.deleteSupplier(1L);
        final List<Supplier> allSuppliers = this.supplierService.retrieveAllSuppliers();
        assertEquals(allSuppliers.size(), 0);

    }


}
