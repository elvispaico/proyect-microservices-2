package com.bank.model.entity;

import com.bank.model.bean.ParameterDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Clase para representa los parametros que se van utilizar
 * en el aplicacion.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "parameters")
public class Parameter {
    private String id;
    private List<ParameterDetail> listDetails;
}
