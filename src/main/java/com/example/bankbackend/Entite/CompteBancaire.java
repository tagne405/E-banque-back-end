package com.example.bankbackend.Entite;

import com.example.bankbackend.Enums.StatusCompte;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
//les deux annotation ci dessous ce pour un mapping SINGLE TABLE
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE" ,length = 4)
@Data @NoArgsConstructor @AllArgsConstructor
public class CompteBancaire {
    @Id
    private String id;
    private double solde;
    private Date creationDate;
    @Enumerated(EnumType.STRING)
    private StatusCompte statusCompte;
    @ManyToOne
    private Client client;
    //EAGER pour charger les operations du compte par contre LAZY qui est celui pardefaut ne l'affiche pas
    @OneToMany(mappedBy = "compteBancaire",fetch = FetchType.LAZY)
    private List<OperationCompte> operationCompteList;
}
