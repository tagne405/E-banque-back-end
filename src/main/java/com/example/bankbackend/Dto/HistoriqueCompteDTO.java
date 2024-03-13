package com.example.bankbackend.Dto;

import lombok.Data;

import java.util.List;

@Data
public class HistoriqueCompteDTO {
    //pour gere la pagination
    private int pageCourant;
    private int PageTotal;
    private int taillePage;
    private String idCompte;
    private double solde;
    private List<OperationCompteDTO> operationCompteDTOS;
}
