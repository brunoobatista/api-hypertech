package com.rancotech.tendtudo.resource;

import com.rancotech.tendtudo.event.RecursoCriadoEvent;
import com.rancotech.tendtudo.model.Fornecedor;
import com.rancotech.tendtudo.repository.FornecedorRepository;
import com.rancotech.tendtudo.repository.filter.FornecedorFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorResource {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Fornecedor> listar(FornecedorFilter fornecedorFilter, Pageable pageable) {
        return fornecedorRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Fornecedor> criar(@Valid @RequestBody Fornecedor fornecedor, HttpServletResponse response) {
        Fornecedor fornecedorSalvo = fornecedorRepository.save(fornecedor);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, fornecedorSalvo.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorSalvo);
    }


}
