package com.rancotech.tendtudo.resource;

import com.rancotech.tendtudo.event.RecursoCriadoEvent;
import com.rancotech.tendtudo.model.Cliente;
import com.rancotech.tendtudo.model.Role;
import com.rancotech.tendtudo.model.Usuario;
import com.rancotech.tendtudo.repository.RoleRepository;
import com.rancotech.tendtudo.repository.UsuarioRepository;
import com.rancotech.tendtudo.repository.filter.UsuarioFilter;
import com.rancotech.tendtudo.service.UsuarioService;
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
@RequestMapping("/usuarios")
public class UsuarioResource {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public Page<Usuario> listar(UsuarioFilter usuarioFilter, Pageable pageable) {
        return this.usuarioRepository.filtrar(usuarioFilter, pageable);
    }

    @GetMapping("/search/{valor}")
    public List<Usuario> procurarUsuario(@PathVariable String valor) {

        return this.usuarioRepository.findByNomeContainsIgnoreCase(valor);
    }

    @PostMapping
    public ResponseEntity<Usuario> salvar(@Valid @RequestBody Usuario usuario, HttpServletResponse response) {
        Usuario usuarioSalvo = this.usuarioService.salvar(usuario);
        this.publisher.publishEvent(new RecursoCriadoEvent(this, response, usuarioSalvo.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorCódigo(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.isPresent() ? ResponseEntity.ok(usuario.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/permissoes")
    public List<Role> listarPermissoes() {
        return this.roleRepository.findAllByNomeNotContains("ROOT");
    }

    @DeleteMapping("/only/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerById(@PathVariable Long id) {
        this.usuarioRepository.deleteById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> remover(@PathVariable Long id, Pageable pageable) {
        Usuario usuario = usuarioService.remover(id, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }


}
