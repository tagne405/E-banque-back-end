package com.example.bankbackend.Entite;

import com.example.bankbackend.Enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class OperationCompte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date DateOperation;
    private double Montant;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    @ManyToOne
    private CompteBancaire compteBancaire;
    private String description;

}
