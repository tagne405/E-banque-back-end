package com.example.bankbackend.Repository;

import com.example.bankbackend.Entite.Client;
import com.example.bankbackend.Entite.CompteBancaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteBancaireRepository extends JpaRepository<CompteBancaire, String> {
}
