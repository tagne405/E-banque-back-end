package com.example.bankbackend.Entite;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue(value = "CC")
@Data @AllArgsConstructor @NoArgsConstructor
public class CompteCourant extends CompteBancaire{
    private double Decouvert;
}
