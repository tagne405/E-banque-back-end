package com.example.bankbackend.Dto;

import com.example.bankbackend.Entite.Client;
import com.example.bankbackend.Entite.OperationCompte;
import com.example.bankbackend.Enums.StatusCompte;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
public class CompteEpargneDTO extends CompteBancaireDTO{

    private String id;
    private double solde;
    private Date creationDate;
    private StatusCompte statusCompte;
    private ClientDTO clientDTO;
    private double tauxInteret;

}
