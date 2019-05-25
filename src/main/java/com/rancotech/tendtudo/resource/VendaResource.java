package com.rancotech.tendtudo.resource;

import com.rancotech.tendtudo.event.RecursoCriadoEvent;
import com.rancotech.tendtudo.model.Venda;
import com.rancotech.tendtudo.repository.VendaRepository;
import com.rancotech.tendtudo.repository.filter.VendaFilter;
import com.rancotech.tendtudo.service.VendaService;
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
@RequestMapping("/vendas")
public class VendaResource {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private VendaService vendaService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping("/listar")
    public List<Venda> findAll() {
        return vendaRepository.findAll();
    }

    @GetMapping
    public Page<Venda> listar(VendaFilter vendaFilter, Pageable pageable) {
        return vendaRepository.filtrar(vendaFilter, pageable);
    }

    @PostMapping
    public ResponseEntity<Venda> salvar(@Valid @RequestBody Venda venda, HttpServletResponse response) {
        Venda vendaSalvo = vendaService.salvar(venda);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, vendaSalvo.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(vendaSalvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> buscarPorCodigo(@PathVariable Long id) {
        Optional<Venda> venda = vendaService.findById(id);
        return venda.isPresent() ? ResponseEntity.ok(venda.get()) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{vendaId}/{produtoId}")
    public ResponseEntity<Venda> remover(@PathVariable(name = "vendaId") Long vendaId, @PathVariable(name = "produtoId") Long produtoId) {
        Venda venda = this.vendaService.removerProduto(vendaId, produtoId);

        return ResponseEntity.status(HttpStatus.OK).body(venda);
    }
}
