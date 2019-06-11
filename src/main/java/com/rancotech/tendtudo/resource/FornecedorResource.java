package com.rancotech.tendtudo.resource;

import com.rancotech.tendtudo.event.RecursoCriadoEvent;
import com.rancotech.tendtudo.model.Fornecedor;
import com.rancotech.tendtudo.model.enumerated.StatusAtivo;
import com.rancotech.tendtudo.repository.FornecedorRepository;
import com.rancotech.tendtudo.repository.filter.FornecedorFilter;
import com.rancotech.tendtudo.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorResource {

    @Autowired
    private FornecedorService fornecedorService;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('READ_FORNECEDOR', 'FULL_FORNECEDOR')")
    public Page<Fornecedor> listar(FornecedorFilter fornecedorFilter, Pageable pageable) {
        return fornecedorRepository.filtrar(fornecedorFilter, pageable);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('WRITE_FORNECEDOR', 'FULL_FORNECEDOR')")
    public ResponseEntity<Fornecedor> criar(@Valid @RequestBody Fornecedor fornecedor, HttpServletResponse response) {
        Fornecedor fornecedorSalvo = fornecedorService.salvar(fornecedor);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, fornecedorSalvo.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorSalvo);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('READ_FORNECEDOR', 'FULL_FORNECEDOR')")
    public ResponseEntity<Fornecedor> buscarPorCodigo(@PathVariable Long id) {
        Optional<Fornecedor> fornecedor = fornecedorRepository.findAllByIdAndAtivoEquals(id, StatusAtivo.ATIVADO);
        return fornecedor.isPresent() ? ResponseEntity.ok(fornecedor.get()) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('WRITE_FORNECEDOR', 'FULL_FORNECEDOR')")
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Fornecedor> remover(@PathVariable Long id, Pageable pageable) {
        Fornecedor fornecedor = fornecedorService.remover(id, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(fornecedor);
    }

}
