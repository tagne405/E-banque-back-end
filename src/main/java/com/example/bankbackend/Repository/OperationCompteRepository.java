package com.example.bankbackend.Repository;

import com.example.bankbackend.Entite.OperationCompte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationCompteRepository extends JpaRepository<OperationCompte, Long> {
     List<OperationCompte> findByCompteBancaireId(String idCompte);
}
