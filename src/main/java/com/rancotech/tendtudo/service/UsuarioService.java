package com.rancotech.tendtudo.service;

import com.rancotech.tendtudo.model.Usuario;
import com.rancotech.tendtudo.model.enumerated.StatusAtivo;
import com.rancotech.tendtudo.repository.UsuarioRepository;
import com.rancotech.tendtudo.service.exception.SenhaConfirmacaoException;
import com.rancotech.tendtudo.service.exception.UsernameObrigatorioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario remover(Long id, Pageable pageable) {
        List<Usuario> usuarios = usuarioRepository.selectClienteNextPage(pageable.getPageSize(), pageable.getPageNumber(), StatusAtivo.ATIVADO.ordinal());
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            usuario.get().setAtivo(StatusAtivo.DESATIVADO);
            usuarioRepository.save(usuario.get());
        }
        //this.usuarioRepository.deleteById(id);

        return usuarios.isEmpty() ? null : usuarios.get(0);
    }

    public Usuario salvar(Usuario usuario) {
        Usuario usuarioVerificado = this.verificarPassword(usuario);
        usuarioVerificado.setAtivo(StatusAtivo.ATIVADO);
        //usuarioRepository.save(usuarioVerificado);
        usuarioRepository.saveAndFlush(usuarioVerificado);
        return usuarioVerificado;
    }

    public Usuario editar(Usuario usuario) {
        Usuario usuarioSalvo = this.usuarioRepository.findById(usuario.getId()).get();

        usuarioSalvo.setEmail(usuario.getEmail());
        usuarioSalvo.setNome(usuario.getNome());
        usuarioSalvo.setCpf(usuario.getCpf() != null ? usuario.getCpf() : "");
        usuarioSalvo.setRoles(usuario.getRoles());
        this.usuarioRepository.saveAndFlush(usuarioSalvo);
        return usuarioSalvo;
    }

    private Usuario verificarPassword(Usuario usuario) {
        Object password = usuario.getPassword();
        Object passwordConfirmacao = usuario.getConfirmPassword();

        if (usuario.getUsername() == null || usuario.getUsername().isEmpty()) {
            throw new UsernameObrigatorioException();
        }

        if (password != null && passwordConfirmacao != null &&
                password.equals(passwordConfirmacao)) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            usuario.setPassword(encoder.encode(password.toString()));
        } else {
            throw new SenhaConfirmacaoException();
        }

        return usuario;
    }

}
