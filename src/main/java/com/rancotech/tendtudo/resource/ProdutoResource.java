package com.rancotech.tendtudo.resource;

import com.rancotech.tendtudo.event.RecursoCriadoEvent;
import com.rancotech.tendtudo.model.Produto;
import com.rancotech.tendtudo.model.enumerated.StatusAtivo;
import com.rancotech.tendtudo.repository.ProdutoRepository;
import com.rancotech.tendtudo.repository.filter.ProdutoFilter;
import com.rancotech.tendtudo.service.ProdutoService;
import com.rancotech.tendtudo.service.UsuarioService;
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
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('READ_PRODUTO', 'FULL_PRODUTO')")
    public Page<Produto> listar(ProdutoFilter filter, Pageable pageable) {
        return produtoRepository.filtrar(filter, pageable);
    }

    @GetMapping("/search/{valor}")
    @PreAuthorize("hasAnyAuthority('READ_PRODUTO', 'FULL_PRODUTO')")
    public List<Produto> buscarTodos(@PathVariable String valor) {
        return produtoRepository.findByNomeContainsIgnoreCaseAndAtivoEqualsOrderById(valor, StatusAtivo.ATIVADO);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('WRITE_PRODUTO', 'FULL_PRODUTO')")
    public ResponseEntity<Produto> salvar(@Valid @RequestBody Produto produto, HttpServletResponse response) {
        Produto produtoSalvo = produtoService.salvar(produto);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, produtoSalvo.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('READ_PRODUTO', 'FULL_PRODUTO')")
    public ResponseEntity<Produto> buscarPorCodigo(@PathVariable Long id) {
        Optional<Produto> produto = produtoRepository.findByIdAndAtivoEquals(id, StatusAtivo.ATIVADO);
        return produto.isPresent() ? ResponseEntity.ok(produto.get()) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('WRITE_PRODUTO', 'FULL_PRODUTO')")
    public ResponseEntity<Produto> remover(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.remover(id, pageable));
    }



}
