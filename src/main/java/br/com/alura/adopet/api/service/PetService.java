package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository repository;

    public List<Pet> listarTodosDisponiveis() {
        List<Pet> pets = repository.findAll();

        return pets.stream()
                .filter(p -> !p.getAdotado()).toList();

    }
}
