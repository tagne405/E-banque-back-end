package com.example.bankbackend.Entite;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Client {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String email;
    @OneToMany(mappedBy = "client")

    //annotation utiliser pour ne pas charge touute la liste de compte bancaire mais pas optinal pour resoudre le probleme utilier plutot les DTO
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<CompteBancaire> compteBancaireList;
}
