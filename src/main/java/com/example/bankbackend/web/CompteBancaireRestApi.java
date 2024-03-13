package com.example.bankbackend.web;

import com.example.bankbackend.Dto.CompteBancaireDTO;
import com.example.bankbackend.Dto.OperationCompteDTO;
import com.example.bankbackend.Service.CompteBancaireService;
import com.example.bankbackend.exception.CompteBancaireNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CompteBancaireRestApi {

    private CompteBancaireService compteBancaireService;


    public CompteBancaireRestApi(CompteBancaireService compteBancaireService) {
        this.compteBancaireService = compteBancaireService;
    }

    @GetMapping("/compte/{idCompte}")
    public CompteBancaireDTO getCompteBancaire(@PathVariable String idCompte) throws CompteBancaireNotFoundException {
        return compteBancaireService.getCompteBancaire(idCompte);
    }

    @GetMapping("/comptes")
    public List<CompteBancaireDTO> listCompteBancaire() {
        return compteBancaireService.compteBancaireList();
    }

    @GetMapping("/compte/{idCompte}/operation")
    public List<OperationCompteDTO> getHistoriqueCompte(@PathVariable String idCompte){
        return compteBancaireService.historiqueCompte(idCompte);
    }
}

