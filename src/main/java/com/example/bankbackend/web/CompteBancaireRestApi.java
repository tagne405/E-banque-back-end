package com.example.bankbackend.web;

import com.example.bankbackend.Dto.CompteBancaireDTO;
import com.example.bankbackend.Dto.HistoriqueCompteDTO;
import com.example.bankbackend.Dto.OperationCompteDTO;
import com.example.bankbackend.Service.CompteBancaireService;
import com.example.bankbackend.exception.CompteBancaireNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CompteBancaireRestApi {

    private CompteBancaireService compteBancaireService;


    public CompteBancaireRestApi(CompteBancaireService compteBancaireService) {
        this.compteBancaireService = compteBancaireService;
    }

    @GetMapping("/comptes/{idCompte}")
    public CompteBancaireDTO getCompteBancaire(@PathVariable String idCompte) throws CompteBancaireNotFoundException {
        return compteBancaireService.getCompteBancaire(idCompte);
    }

    @GetMapping("/comptes")
    public List<CompteBancaireDTO> listCompteBancaire() {
        return compteBancaireService.compteBancaireList();
    }

    @GetMapping("/comptes/{idCompte}/operation")
    public List<OperationCompteDTO> getHistorique(@PathVariable String idCompte){
        return compteBancaireService.historiqueCompte(idCompte);
    }

    @GetMapping("/comptes/{idCompte}/operationPage")
    public HistoriqueCompteDTO getHistoriqueCompte(@PathVariable String idCompte,
                                                   @RequestParam(name="page", defaultValue = "0") int page,
                                                   @RequestParam(name = "taille", defaultValue = "5") int taille) throws CompteBancaireNotFoundException {
        return compteBancaireService.getHistoriqueCompte(idCompte,page,taille);
    }
}

