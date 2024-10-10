package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.execpetion.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbrigoService {
    @Autowired
    private AbrigoRepository repository;


    public List<Abrigo> listar() {
        return repository.findAll();
    }
    public void cadastrar(Abrigo abrigo) {

        if (repository.existsByNome(abrigo.getNome()) || repository.existsByTelefone(abrigo.getTelefone()) || repository.existsByEmail(abrigo.getEmail())) {
            throw new ValidacaoException("Dados já cadastrados para outro abrigo!");
        }
        repository.save(abrigo);
    }

    public List<Pet> listarPets(String idOuNome) {
        try {
            Long id = Long.parseLong(idOuNome);
            return repository.getReferenceById(id).getPets();
        } catch (EntityNotFoundException enfe) {
            throw new ValidacaoException("Pet nõo encontrado!");
        } catch (NumberFormatException e) {
            try {
                return repository.findByNome(idOuNome).getPets();
            } catch (EntityNotFoundException enfe) {
                throw new ValidacaoException("Pet nõo encontrado!");
            }
        }
    }


    public void cadastrarPet(String idOuNome, Pet pet) {
        try {
            Long id = Long.parseLong(idOuNome);
            Abrigo abrigo = repository.getReferenceById(id);
            pet.setAbrigo(abrigo);
            pet.setAdotado(false);
            abrigo.getPets().add(pet);
            repository.save(abrigo);

        } catch (EntityNotFoundException enfe) {
            throw new ValidacaoException("Erro ao cadastrar pet!");
        } catch (NumberFormatException nfe) {
            try {
                Abrigo abrigo = repository.findByNome(idOuNome);
                pet.setAbrigo(abrigo);
                pet.setAdotado(false);
                abrigo.getPets().add(pet);
                repository.save(abrigo);
            } catch (EntityNotFoundException enfe) {
                throw new ValidacaoException("Pet já cadastrado!");
            }
        }
    }
}

