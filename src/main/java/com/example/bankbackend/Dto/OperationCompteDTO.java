package com.example.bankbackend.Dto;

import com.example.bankbackend.Entite.CompteBancaire;
import com.example.bankbackend.Enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class OperationCompteDTO {

    private Long id;
    private Date DateOperation;
    private double Montant;
    private OperationType type;
    private String description;

}
