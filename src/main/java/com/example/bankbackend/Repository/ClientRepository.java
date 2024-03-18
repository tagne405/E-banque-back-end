package com.example.bankbackend.Repository;

import com.example.bankbackend.Dto.ClientDTO;
import com.example.bankbackend.Entite.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findClientByNomContains(String motCle);
}
