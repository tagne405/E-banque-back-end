package com.example.bankbackend;

import com.example.bankbackend.Dto.ClientDTO;
import com.example.bankbackend.Dto.CompteBancaireDTO;
import com.example.bankbackend.Dto.CompteCourantDTO;
import com.example.bankbackend.Dto.CompteEpargneDTO;
import com.example.bankbackend.Entite.*;
import com.example.bankbackend.Enums.OperationType;
import com.example.bankbackend.Enums.StatusCompte;
import com.example.bankbackend.Repository.ClientRepository;
import com.example.bankbackend.Repository.CompteBancaireRepository;
import com.example.bankbackend.Repository.OperationCompteRepository;
import com.example.bankbackend.Service.CompteBancaireService;
import com.example.bankbackend.exception.ClientNotFoundException;
import com.example.bankbackend.exception.CompteBancaireNotFoundException;
import com.example.bankbackend.exception.MontantNotFoundException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class BankBackendApplication {


    public static void main(String[] args) {
        SpringApplication.run(BankBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CompteBancaireService compteBancaireService){
        return args -> {
            Stream.of("TAGNE","Yannick","Jordan").forEach(nom->{
                ClientDTO client = new ClientDTO();
                client.setNom(nom);
                client.setEmail(nom+"@gmail.com");
                compteBancaireService.saveClient(client);
            });
          compteBancaireService.listClient().forEach(client -> {
              try {
                  compteBancaireService.saveCompteBancaireCourant(Math.random()*45,5000,client.getId());
                  compteBancaireService.saveCompteBancaireEpargne(Math.random()*454544,1.2,client.getId());

              } catch (ClientNotFoundException  e) {
                  e.printStackTrace();
              }
          });
            List<CompteBancaireDTO> compteBancaireList = compteBancaireService.compteBancaireList();
            for (CompteBancaireDTO compteBancaire:compteBancaireList){
                for(int i=0; i<10 ;i++){
                    String idCompte;
                    if (compteBancaire instanceof CompteEpargneDTO){
                        idCompte = ((CompteEpargneDTO) compteBancaire).getId();
                    }else{
                        idCompte = ((CompteCourantDTO) compteBancaire).getId();
                    }
                    compteBancaireService.credit(idCompte,10000+Math.random()*120000,"CREDIT");
                    compteBancaireService.debit(idCompte,1000+ Math.random()*9000,"DEBIT");
                }
            }
        };

    }

    //@Bean
    CommandLineRunner commandLineRunner(CompteBancaireRepository compteBancaireRepository){
        return args -> {
            CompteBancaire compteBancaire =
                    compteBancaireRepository.findById("118aa282-7928-44ae-a4f0-8a7ba4ff8736").orElse(null);
            if (compteBancaire != null) {
                System.out.println("************************************");
                System.out.println(compteBancaire.getId());
                System.out.println(compteBancaire.getStatusCompte());
                System.out.println(compteBancaire.getClient().getNom());
                System.out.println(compteBancaire.getCreationDate());
                System.out.println(compteBancaire.getSolde());
                System.out.println(compteBancaire.getClass().getSimpleName());
                if (compteBancaire instanceof CompteCourant) {
                    System.out.println("Decouvert " + ((CompteCourant) compteBancaire).getDecouvert());
                } else if (compteBancaire instanceof CompteEpargne) {
                    System.out.println("Taux dinteret" + ((CompteEpargne) compteBancaire).getTauxInteret());
                }
                compteBancaire.getOperationCompteList().forEach(op -> {
                    System.out.println("===================================");
                    System.out.println(op.getType());
                    System.out.println(op.getMontant());
                    System.out.println(op.getDateOperation());
                });
            }
        };
    }

    //@Bean
    CommandLineRunner start(ClientRepository clientRepository,
                            CompteBancaireRepository compteBancaireRepository,
                            OperationCompteRepository operationCompteRepository){
        return args -> {
            Stream.of("Yannick","Tagne","Jordan").forEach(nom->{
                Client client= new Client();
                client.setNom(nom);
                client.setEmail(nom+"@gmail.com");
                clientRepository.save(client);
            });
            clientRepository.findAll().forEach(client -> {
                CompteCourant compteCourant=new CompteCourant();
                compteCourant.setId(UUID.randomUUID().toString());
                compteCourant.setSolde(Math.random());
                compteCourant.setCreationDate(new Date());
                compteCourant.setStatusCompte(StatusCompte.CREATION);
                compteCourant.setDecouvert(5400);
                compteCourant.setClient(client);

                compteBancaireRepository.save(compteCourant);
            });

            clientRepository.findAll().forEach(client -> {
                CompteEpargne compteEpargne=new CompteEpargne();
                compteEpargne.setId(UUID.randomUUID().toString());
                compteEpargne.setSolde(Math.random());
                compteEpargne.setCreationDate(new Date());
                compteEpargne.setStatusCompte(StatusCompte.CREATION);
                compteEpargne.setTauxInteret(5);
                compteEpargne.setClient(client);

                compteBancaireRepository.save(compteEpargne);
            });

            compteBancaireRepository.findAll().forEach(compte->{
                for(int i=0; i<9; i++){
                    OperationCompte operationCompte = new OperationCompte();
                    operationCompte.setDateOperation(new Date());
                    operationCompte.setMontant(Math.random()*45);
                    operationCompte.setType(Math.random()>0.5? OperationType.DEBIT :OperationType.CREDIT);
                    operationCompte.setCompteBancaire(compte);

                    operationCompteRepository.save(operationCompte);
                }


            });

        };

    }
}
