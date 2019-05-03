package com.rancotech.tendtudo.resource;

import com.rancotech.tendtudo.event.RecursoCriadoEvent;
import com.rancotech.tendtudo.model.Produto;
import com.rancotech.tendtudo.repository.ProdutoRepository;
import com.rancotech.tendtudo.repository.filter.ProdutoFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public Page<Produto> listar(ProdutoFilter filter, Pageable pageable) {
        return produtoRepository.filtrar(filter, pageable);
    }

    @GetMapping("/search/{valor}")
    public List<Produto> buscarTodos(@PathVariable String valor) {
        return produtoRepository.findByNomeContainsIgnoreCaseOrderById(valor);
    }

    @PostMapping
    public ResponseEntity<Produto> salvar(@Valid @RequestBody Produto produto, HttpServletResponse response) {
        Produto produtoSalvo = produtoRepository.save(produto);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, produtoSalvo.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorCodigo(@PathVariable Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.isPresent() ? ResponseEntity.ok(produto.get()) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        produtoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }



}
