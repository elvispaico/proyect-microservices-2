package com.bank.model.response;



import com.bank.model.bean.ProfileCustomer;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {
    private String id;
    private String name;
    private String numDocument;
    private String codTypeCustomer;
    private String desTypeCustomer;
    private ProfileCustomer profile;
}
