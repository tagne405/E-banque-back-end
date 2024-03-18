package com.example.bankbackend.Service;

import com.example.bankbackend.Dto.*;
import com.example.bankbackend.Entite.Client;
import com.example.bankbackend.Entite.CompteBancaire;
import com.example.bankbackend.Entite.CompteEpargne;
import com.example.bankbackend.exception.ClientNotFoundException;
import com.example.bankbackend.exception.CompteBancaireNotFoundException;
import com.example.bankbackend.exception.MontantNotFoundException;

import java.util.List;

public interface CompteBancaireService {
    ClientDTO saveClient(ClientDTO clientDTO);
    CompteCourantDTO saveCompteBancaireCourant(double soldeInitial, double decouvert, Long clientId) throws ClientNotFoundException;
    CompteEpargneDTO saveCompteBancaireEpargne(double soldeInitial, double tauxInteret, Long clientId) throws ClientNotFoundException;
    List<ClientDTO> listClient();

    //retrouve un client par son
    List<ClientDTO> rechercheClient(String motCle);

    CompteBancaireDTO getCompteBancaire(String  compteId) throws CompteBancaireNotFoundException;
    void debit (String compteId, double montant, String description) throws CompteBancaireNotFoundException, MontantNotFoundException;
    void credit(String compteId, double montant, String description) throws CompteBancaireNotFoundException;
    void transfert (String idCompteSource, String idComptedestinataire, double montant) throws MontantNotFoundException, CompteBancaireNotFoundException;

    List<CompteBancaireDTO> compteBancaireList();

    ClientDTO getClient(Long clientId) throws ClientNotFoundException;

    ClientDTO updateClient(ClientDTO clientDTO);

    void deleteClient(Long clientId);

    List<OperationCompteDTO> historiqueCompte(String idCompte);

    HistoriqueCompteDTO getHistoriqueCompte(String idCompte, int page, int taille) throws CompteBancaireNotFoundException;
}
