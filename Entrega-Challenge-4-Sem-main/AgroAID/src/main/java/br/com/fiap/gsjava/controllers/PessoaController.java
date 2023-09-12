package br.com.fiap.gsjava.controllers;

import br.com.fiap.gsjava.models.PessoaFisica;
import br.com.fiap.gsjava.repositories.PessoaRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.data.domain.Pageable;
import org.slf4j.Logger;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {
    Logger log = LoggerFactory.getLogger(PessoaController.class);

    @Autowired
    PessoaRepository repo;

    @Autowired
    PagedResourcesAssembler<PessoaFisica> assembler;

    @GetMapping
    public PagedModel<EntityModel<PessoaFisica>>  index(@PageableDefault(size = 5) Pageable pageable){
        return assembler.toModel(repo.findAll(pageable));
    }

    @GetMapping("/busca/{id}")
    public EntityModel<PessoaFisica> show(@PathVariable Long id) {
        log.info("buscar pessoa com id: " + id);
        PessoaFisica pessoa = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada"));
        return pessoa.toModel();
    }

    @PostMapping
    public ResponseEntity<PessoaFisica> create(@RequestBody @Valid PessoaFisica pessoa) {
        log.info("cadastrar pessoa: " + pessoa);
        repo.save(pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaFisica> update(@PathVariable Long id, @RequestBody @Valid PessoaFisica pessoaAtualizada) {
        log.info("atualizar a pessoa com id: " + id);
        PessoaFisica pessoa = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada"));
        BeanUtils.copyProperties(pessoaAtualizada, pessoa, "id");
        repo.save(pessoa);
        return ResponseEntity.ok(pessoa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PessoaFisica> destroy(@PathVariable Long id) {
        log.info("deletar pessoa com o id: " + id);
        PessoaFisica pessoa = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada"));;
        repo.delete(pessoa);
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleValidationExceptions(ConstraintViolationException ex) {
        log.error("Erro de validação: ", ex);
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        log.error("Erro não esperado: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro inesperado. Tente novamente mais tarde.");
    }
}
