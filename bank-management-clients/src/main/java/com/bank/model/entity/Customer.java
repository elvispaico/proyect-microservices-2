package com.bank.model.entity;

import com.bank.model.bean.ProfileCustomer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Clase representa los clientes del banco
 */
@NoArgsConstructor
@Setter
@Getter
@Document(collection = "clients")
public class Customer {
    @Id
    private String id;
    private String name;
    private String numDocument;
    //tipo de clientes : personal:01|empresarial:02
    private String codTypeCustomer;
    private String desTypeCustomer;
    private ProfileCustomer profile;
}
