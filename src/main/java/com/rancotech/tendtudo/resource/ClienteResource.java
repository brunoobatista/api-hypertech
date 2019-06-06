package com.rancotech.tendtudo.resource;

import com.rancotech.tendtudo.event.RecursoCriadoEvent;
import com.rancotech.tendtudo.model.Cliente;
import com.rancotech.tendtudo.repository.ClienteRepository;
import com.rancotech.tendtudo.repository.filter.ClienteFilter;
import com.rancotech.tendtudo.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.tags.Param;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('READ_CLIENTE', 'FULL_CLIENTE')")
    public Page<Cliente> listar(ClienteFilter clienteFilter, Pageable pageable) {
        return clienteRepository.filtrar(clienteFilter, pageable);
    }

    @GetMapping("/search/{valor}")
    @PreAuthorize("hasAnyAuthority('READ_CLIENTE', 'FULL_CLIENTE')")
    public List<Cliente> procurarCliente(@PathVariable String valor) {

        return clienteRepository.findByNomeContainsIgnoreCase(valor);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('WRITE_CLIENTE', 'FULL_CLIENTE')")
    public ResponseEntity<Cliente> salvar(@Valid @RequestBody Cliente cliente, HttpServletResponse response) {
        Cliente clienteSalvo= clienteService.salvar(cliente);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, clienteSalvo.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('READ_CLIENTE', 'FULL_CLIENTE')")
    public ResponseEntity<Cliente> buscarPorCódigo(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.isPresent() ? ResponseEntity.ok(cliente.get()) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/only/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('WRITE_CLIENTE', 'FULL_CLIENTE')")
    public void removerById(@PathVariable Long id) {
        this.clienteRepository.deleteById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('WRITE_CLIENTE', 'FULL_CLIENTE')")
    public ResponseEntity<Cliente> remover(@PathVariable Long id, Pageable pageable) {
        Cliente cliente = clienteService.remover(id, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

}
