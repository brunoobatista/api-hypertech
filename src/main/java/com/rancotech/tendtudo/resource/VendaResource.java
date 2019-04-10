package com.rancotech.tendtudo.resource;

import com.rancotech.tendtudo.model.Venda;
import com.rancotech.tendtudo.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vendas")
public class VendaResource {

    @Autowired
    private VendaRepository vendaRepository;

    @GetMapping
    public List<Venda> listar() {
        return vendaRepository.findAll();
    }

   

    @GetMapping("/{id}")
    public ResponseEntity<Venda> buscarPorCodigo(@PathVariable Long id) {
        Optional<Venda> venda = vendaRepository.findById(id);
        return venda.isPresent() ? ResponseEntity.ok(venda.get()) : ResponseEntity.notFound().build();
    }
}
