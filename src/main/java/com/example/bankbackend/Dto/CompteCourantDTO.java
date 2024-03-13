package com.example.bankbackend.Dto;

import com.example.bankbackend.Enums.StatusCompte;
import lombok.Data;

import java.util.Date;


@Data
public class CompteCourantDTO extends CompteBancaireDTO{

    private String id;
    private double solde;
    private Date creationDate;
    private StatusCompte statusCompte;
    private ClientDTO clientDTO;
    private double decouvert;

}
