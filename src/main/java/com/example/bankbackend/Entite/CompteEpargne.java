package com.example.bankbackend.Entite;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("CE")
@Data @AllArgsConstructor @NoArgsConstructor
public class CompteEpargne extends CompteBancaire{
    private double TauxInteret;
}
