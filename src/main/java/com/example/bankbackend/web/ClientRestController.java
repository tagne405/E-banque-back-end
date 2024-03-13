package com.example.bankbackend.web;

import com.example.bankbackend.Dto.ClientDTO;
import com.example.bankbackend.Entite.Client;
import com.example.bankbackend.Service.CompteBancaireService;
import com.example.bankbackend.exception.ClientNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class ClientRestController {
    private CompteBancaireService compteBancaireService;

    @GetMapping("/client")
    public List<ClientDTO> clientList(){
        return compteBancaireService.listClient();
    }

    @GetMapping("/client/{id}")
    public ClientDTO getClient(@PathVariable(name = "id") Long idClient) throws ClientNotFoundException {
        return compteBancaireService.getClient(idClient);
    }

    @PostMapping("/client")
    public ClientDTO saveClient(@RequestBody ClientDTO clientDTO){
        return compteBancaireService.saveClient(clientDTO);
    }

    @PutMapping("/client/{idClient}")
    public ClientDTO updateClient(@PathVariable Long idClient  ,@RequestBody ClientDTO clientDTO){
        clientDTO.setId(idClient);
        return compteBancaireService.updateClient(clientDTO);
    }

    @DeleteMapping("/client/{id}")
    public void deleteClient(@PathVariable Long id){
        compteBancaireService.deleteClient(id);
    }
}
