package com.rancotech.tendtudo.resource;

import com.rancotech.tendtudo.event.RecursoCriadoEvent;
import com.rancotech.tendtudo.model.Venda;
import com.rancotech.tendtudo.model.enumerated.StatusVenda;
import com.rancotech.tendtudo.repository.VendaRepository;
import com.rancotech.tendtudo.repository.filter.VendaFilter;
import com.rancotech.tendtudo.service.VendaService;
import com.rancotech.tendtudo.service.exception.AtualizarVendaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
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
    public ResponseEntity<Venda> salvarEmAberto(@Valid @RequestBody Venda venda, HttpServletResponse response) {
        if (venda.getStatus().equalsIgnoreCase(StatusVenda.ABERTA.toString())) {
            venda.setStatus(StatusVenda.ABERTA.toString());
            return ResponseEntity.status(HttpStatus.CREATED).body(this.salvar(venda, response));
        } else {
            throw new AtualizarVendaException();
        }
    }

    @PostMapping("/finalizar")
    public ResponseEntity<Venda> salvarFinalizar(@Valid @RequestBody Venda venda, HttpServletResponse response) {
        if (venda.getStatus().equalsIgnoreCase(StatusVenda.ABERTA.toString())) {
            venda.setStatus(StatusVenda.FINALIZADA.toString());
            return ResponseEntity.status(HttpStatus.CREATED).body(this.salvar(venda, response));
        } else {
            throw new AtualizarVendaException();
        }
    }

    @PostMapping("/cancelar")
    public ResponseEntity<Venda> cancelar(@Valid @RequestBody Venda venda, HttpServletResponse response) {
        if (venda.getStatus().equalsIgnoreCase(StatusVenda.ABERTA.toString())) {
            venda.setStatus(StatusVenda.CANCELADA.toString());
            return ResponseEntity.status(HttpStatus.OK).body(this.salvar(venda, response));
        } else {
            throw new AtualizarVendaException();
        }
    }

    @PutMapping("/estornar")
    public ResponseEntity<Venda> estornar(@Valid @RequestBody Venda venda) {
        if (venda.getStatus().equalsIgnoreCase(StatusVenda.FINALIZADA.toString())) {
            venda.setStatus(StatusVenda.ESTORNADA.toString());
            vendaRepository.save(venda);
            return ResponseEntity.ok(venda);
        } else {
            throw new AtualizarVendaException();
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> buscarPorCodigo(@PathVariable Long id) {
        Optional<Venda> venda = vendaService.findById(id);
        return venda.isPresent() ? ResponseEntity.ok(venda.get()) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        this.vendaService.remover(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{vendaId}/{produtoId}")
    public ResponseEntity<Venda> removerProduto(@PathVariable(name = "vendaId") Long vendaId, @PathVariable(name = "produtoId") Long produtoId) {
        Venda venda = this.vendaService.removerProduto(vendaId, produtoId);

        return ResponseEntity.status(HttpStatus.OK).body(venda);
    }

    private Venda salvar(Venda venda, HttpServletResponse response) {
        Venda vendaSalvo = vendaService.salvar(venda);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, vendaSalvo.getId()));
        return vendaSalvo;
    }
}
