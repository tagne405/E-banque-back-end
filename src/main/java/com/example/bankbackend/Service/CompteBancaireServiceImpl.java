package com.example.bankbackend.Service;

import com.example.bankbackend.Dto.*;
import com.example.bankbackend.Entite.*;
import com.example.bankbackend.Enums.OperationType;
import com.example.bankbackend.Mappers.CompteBancaireMapperImpl;
import com.example.bankbackend.Repository.ClientRepository;
import com.example.bankbackend.Repository.CompteBancaireRepository;
import com.example.bankbackend.Repository.OperationCompteRepository;
import com.example.bankbackend.exception.ClientNotFoundException;
import com.example.bankbackend.exception.CompteBancaireNotFoundException;
import com.example.bankbackend.exception.MontantNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@Slf4j
public class CompteBancaireServiceImpl implements CompteBancaireService {

    private ClientRepository clientRepository;
    private CompteBancaireRepository compteBancaireRepository;
    private OperationCompteRepository operationCompteRepository;
    private CompteBancaireMapperImpl dtoMapper;

    //injection de dependence
    public CompteBancaireServiceImpl(ClientRepository clientRepository,
                                     CompteBancaireRepository compteBancaireRepository,
                                     OperationCompteRepository operationCompteRepository,
                                     CompteBancaireMapperImpl dtoMapper) {
        this.clientRepository = clientRepository;
        this.compteBancaireRepository = compteBancaireRepository;
        this.operationCompteRepository = operationCompteRepository;
        this.dtoMapper = dtoMapper;
    }

    //log pour logger les messages a retire pour utiliser l'annotation @slf4j
    //Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public ClientDTO saveClient(ClientDTO clientDTO) {
        //log de sont annotation @slf4j permet de logger des messages
        log.info("Enregistrement nouveaux client");
        Client client = dtoMapper.deClientDTO(clientDTO);
        Client saveClient = clientRepository.save(client);
        return dtoMapper.deClient(saveClient);
    }

    @Override
    //CompteCourantDto retourne parcequ'on recois un objet de compteCourant et puis on renvoie et compteCourantDTO
    public CompteCourantDTO saveCompteBancaireCourant(double soldeInitial, double decouvert, Long clientId) throws ClientNotFoundException {
        Client client = clientRepository.findById(clientId).orElse(null);
        if(client == null)
            throw new ClientNotFoundException("client ne correspond pas");

        CompteCourant compteBancaireCourant = new CompteCourant();
        compteBancaireCourant.setId(UUID.randomUUID().toString());
        compteBancaireCourant.setCreationDate(new Date());
        compteBancaireCourant.setSolde(soldeInitial);
        compteBancaireCourant.setClient(client);
        compteBancaireCourant.setDecouvert(decouvert);
        return dtoMapper.deCompteCourant(compteBancaireRepository.save(compteBancaireCourant));

    }

    @Override
    public CompteEpargneDTO saveCompteBancaireEpargne(double soldeInitial, double tauxInteret, Long clientId) throws ClientNotFoundException {
        Client client = clientRepository.findById(clientId).orElse(null);
        if(client == null)
            throw new ClientNotFoundException("client ne correspond pas");

        CompteEpargne compteBancaireEpargne = new CompteEpargne();
        compteBancaireEpargne.setId(UUID.randomUUID().toString());
        compteBancaireEpargne.setCreationDate(new Date());
        compteBancaireEpargne.setSolde(soldeInitial);
        compteBancaireEpargne.setClient(client);
        compteBancaireEpargne.setTauxInteret(tauxInteret);
        return dtoMapper.deCompteEpargne(compteBancaireRepository.save(compteBancaireEpargne));
    }


    @Override
    public List<ClientDTO> listClient() {
        List<Client> clients = clientRepository.findAll();
        //clients.stream().map(client -> dtoMapper.deClient(client)).collect(Collectors.toList());
        List<ClientDTO> clientDTOS = new ArrayList<>();
        for (Client client:clients){
            ClientDTO clientDTO = dtoMapper.deClient(client);
            clientDTOS.add(clientDTO);
        }
        return clientDTOS;
    }

    @Override
    public CompteBancaireDTO getCompteBancaire(String compteId) throws CompteBancaireNotFoundException {
        CompteBancaire compteBancaire = compteBancaireRepository.findById(compteId)
                .orElseThrow(() -> new CompteBancaireNotFoundException("Compte pas trouve"));
        if (compteBancaire instanceof CompteEpargne){
            CompteEpargne compteEpargne = (CompteEpargne) compteBancaire;
            return dtoMapper.deCompteEpargne(compteEpargne);
        }else {
            CompteCourant compteCourant = (CompteCourant) compteBancaire;
            return dtoMapper.deCompteCourant(compteCourant);
        }
    }

    @Override
    public void debit(String compteId, double montant, String description) throws CompteBancaireNotFoundException, MontantNotFoundException {
        CompteBancaire compteBancaire = compteBancaireRepository.findById(compteId)
                .orElseThrow(() -> new CompteBancaireNotFoundException("Compte pas trouve"));

        if(compteBancaire.getSolde()<montant)
            throw new MontantNotFoundException("Solde insufisant");
        OperationCompte operationCompte = new OperationCompte();
        operationCompte.setType(OperationType.DEBIT);
        operationCompte.setMontant(montant);
        operationCompte.setDescription(description);
        operationCompte.setDateOperation(new Date());
        operationCompte.setCompteBancaire(compteBancaire);
        operationCompteRepository.save(operationCompte);

        compteBancaire.setSolde(compteBancaire.getSolde()-montant);
        compteBancaireRepository.save(compteBancaire);
    }

    @Override
    public void credit(String compteId, double montant, String description) throws CompteBancaireNotFoundException {
        //faut cree une methode pour gere la redondance de cet methode
        CompteBancaire compteBancaire = compteBancaireRepository.findById(compteId)
                .orElseThrow(() -> new CompteBancaireNotFoundException("Compte pas trouve"));

        OperationCompte operationCompte = new OperationCompte();
        operationCompte.setType(OperationType.CREDIT);
        operationCompte.setMontant(montant);
        operationCompte.setDescription(description);
        operationCompte.setDateOperation(new Date());
        operationCompte.setCompteBancaire(compteBancaire);
        operationCompteRepository.save(operationCompte);

        compteBancaire.setSolde(compteBancaire.getSolde()+montant);
        compteBancaireRepository.save(compteBancaire);
    }

    @Override
    public void transfert(String idCompteSource, String idComptedestinataire, double montant) throws MontantNotFoundException, CompteBancaireNotFoundException {
        debit(idCompteSource,montant,"Transfer a" + idComptedestinataire);
        credit(idComptedestinataire,montant,"Transfer de" + idCompteSource);
    }

    @Override
    public List<CompteBancaireDTO> compteBancaireList(){
        List<CompteBancaire> listeCompteBancaire = compteBancaireRepository.findAll();
        List<CompteBancaireDTO> compteBancaireDTOS = listeCompteBancaire.stream().map(list -> {
            if (list instanceof CompteEpargne) {
                CompteEpargne compteEpargne = (CompteEpargne) list;
                return dtoMapper.deCompteEpargne(compteEpargne);
            } else {
                CompteCourant compteCourant = (CompteCourant) list;
                return dtoMapper.deCompteCourant(compteCourant);
            }
        }).collect(Collectors.toList());

        return compteBancaireDTOS;
    }

    @Override
    public ClientDTO getClient(Long clientId) throws ClientNotFoundException {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new ClientNotFoundException("Cllient pas retrouve"));
        return dtoMapper.deClient(client);
    }

    @Override
    public ClientDTO updateClient(ClientDTO clientDTO) {
        log.info("Enregistrement nouveaux client");
        Client client = dtoMapper.deClientDTO(clientDTO);
        Client saveClient = clientRepository.save(client);
        return dtoMapper.deClient(saveClient);
    }

    @Override
    public void deleteClient(Long clientId){
        clientRepository.deleteById(clientId);
    }

    @Override
    public List<OperationCompteDTO> historiqueCompte(String idCompte){
        log.info("historique");
        List<OperationCompte> operationCompteById = operationCompteRepository.findByCompteBancaire_Id(idCompte);
        return operationCompteById.stream().map(op->dtoMapper.deOperationCompte(op)).collect(Collectors.toList());
    }

    @Override
    public HistoriqueCompteDTO getHistoriqueCompte(String idCompte, int page, int taille) throws CompteBancaireNotFoundException {
        CompteBancaire compteBancaire = compteBancaireRepository.findById(idCompte).orElse(null);
        if (compteBancaire == null) throw  new CompteBancaireNotFoundException("Compte Bancaire pas retrouve");
        Page<OperationCompte> operationCompte = operationCompteRepository.findByCompteBancaireId(idCompte, PageRequest.of(page, taille));
        HistoriqueCompteDTO historiqueCompteDTO = new HistoriqueCompteDTO();
        List<OperationCompteDTO> operationCompteDTO =  operationCompte.getContent().stream().map(operationCompte1 -> dtoMapper.deOperationCompte(operationCompte1)).collect(Collectors.toList());

       //pense a ajouter ces transfert dans le mapper

        historiqueCompteDTO.setOperationCompteDTOS(operationCompteDTO);
        historiqueCompteDTO.setIdCompte(compteBancaire.getId());
        historiqueCompteDTO.setSolde(compteBancaire.getSolde());
        historiqueCompteDTO.setTaillePage(taille);
        historiqueCompteDTO.setPageCourant(page);
        historiqueCompteDTO.setPageTotal(operationCompte.getTotalPages());

        return historiqueCompteDTO;
    }
}
