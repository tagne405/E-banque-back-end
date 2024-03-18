package com.example.bankbackend.Mappers;

import com.example.bankbackend.Dto.*;
import com.example.bankbackend.Entite.Client;
import com.example.bankbackend.Entite.CompteCourant;
import com.example.bankbackend.Entite.CompteEpargne;
import com.example.bankbackend.Entite.OperationCompte;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CompteBancaireMapperImpl {
    public ClientDTO deClient(Client client){
        ClientDTO clientDTO = new ClientDTO();

        //transfert de donne dynamique
        BeanUtils.copyProperties(client,clientDTO);

        /*
        transfert statique
        clientDTO.setId(client.getId());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setNom(client.getNom());
        */
        return clientDTO;
    }

    public Client deClientDTO(ClientDTO clientDTO){
        Client client = new Client();
        BeanUtils.copyProperties(clientDTO,client);
        return client;
    }

    public CompteEpargneDTO deCompteEpargne(CompteEpargne compteEpargne){
        CompteEpargneDTO compteEpargneDTO = new CompteEpargneDTO();
        BeanUtils.copyProperties(compteEpargne,compteEpargneDTO);
        //recupere et transfer du client de compteEpargne->compteEpargneDTO
        compteEpargneDTO.setClientDTO(deClient(compteEpargne.getClient()));
        compteEpargneDTO.setType(compteEpargne.getClass().getSimpleName());
        return  compteEpargneDTO;
    }

    public CompteEpargne deCompteEpargneDTO(CompteEpargneDTO compteEpargneDTO){
        CompteEpargne compteEpargne = new CompteEpargne();
        BeanUtils.copyProperties(compteEpargneDTO,compteEpargne);
        //recupere et transfer du client de compteEpargneDTO->compteEpargne
        compteEpargne.setClient(deClientDTO(compteEpargneDTO.getClientDTO()));
        return  compteEpargne;
    }

    public CompteCourantDTO deCompteCourant(CompteCourant compteCourant){
        CompteCourantDTO compteCourantDTO = new CompteCourantDTO();
        BeanUtils.copyProperties(compteCourant,compteCourantDTO);
        compteCourantDTO.setClientDTO(deClient(compteCourant.getClient()));
        compteCourantDTO.setType(compteCourant.getClass().getSimpleName());
        return  compteCourantDTO;
    }

    public CompteCourant deCompteCourantDTO(CompteCourantDTO compteCourantDTO){
        CompteCourant compteCourant = new CompteCourant();
        BeanUtils.copyProperties(compteCourantDTO,compteCourantDTO);
        compteCourant.setClient(deClientDTO(compteCourantDTO.getClientDTO()));
        return  compteCourant;
    }

    public OperationCompteDTO deOperationCompte(OperationCompte operationCompte){
        OperationCompteDTO operationCompteDTO = new OperationCompteDTO();
        BeanUtils.copyProperties(operationCompte,operationCompteDTO);
        return operationCompteDTO;
    }

    public HistoriqueCompteDTO deHistoriqueCompte(HistoriqueCompteDTO historiqueCompteDTO){
        HistoriqueCompteDTO historiqueCompteDTO1 = new HistoriqueCompteDTO();
        BeanUtils.copyProperties(historiqueCompteDTO,historiqueCompteDTO1);
        return historiqueCompteDTO1;
    }
}
