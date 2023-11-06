package tn.esprit.devops_project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceDTO {
    Long idInvoice;
    float amountDiscount;
    float amountInvoice;
    Date dateCreationInvoice;
    Date dateLastModificationInvoice;
    Boolean archived;

}
