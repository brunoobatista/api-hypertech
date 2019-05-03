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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
    public Page<Cliente> listar(ClienteFilter clienteFilter, Pageable pageable) {
        return clienteRepository.filtrar(clienteFilter, pageable);
    }

    @GetMapping("/search/{valor}")
    public List<Cliente> procurarCliente(@PathVariable String valor) {

        return clienteRepository.findByNomeContainsIgnoreCase(valor);
    }

    @PostMapping
    public ResponseEntity<Cliente> salvar(@Valid @RequestBody Cliente cliente, HttpServletResponse response) {
        Cliente clienteSalvo= clienteService.salvar(cliente);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, clienteSalvo.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorCódigo(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.isPresent() ? ResponseEntity.ok(cliente.get()) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        clienteRepository.deleteById(id);
    }

}
