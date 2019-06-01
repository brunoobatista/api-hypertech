package com.rancotech.tendtudo.resource;

import com.rancotech.tendtudo.model.Permissao;
import com.rancotech.tendtudo.model.Usuario;
import com.rancotech.tendtudo.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {

    @Autowired
    private PermissaoRepository permissaoRepository;

    @PostMapping
    public ResponseEntity<Usuario> salvar(@Valid @RequestBody Usuario usuario) {
        return null;
    }

    @GetMapping("/permissoes")
    public List<Permissao> listarPermissoes() {
        return this.permissaoRepository.findAll();
    }

}
