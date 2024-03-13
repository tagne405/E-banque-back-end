package com.example.bankbackend.Repository;

import com.example.bankbackend.Entite.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
