package com.rancotech.tendtudo.service;

import com.rancotech.tendtudo.model.Usuario;
import com.rancotech.tendtudo.repository.UsuarioRepository;
import com.rancotech.tendtudo.service.exception.SenhaConfirmacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario remover(Long id, Pageable pageable) {
        List<Usuario> usuarios = usuarioRepository.selectClienteNextPage(pageable.getPageSize(), pageable.getPageNumber());
        this.usuarioRepository.deleteById(id);

        return usuarios.isEmpty() ? null : usuarios.get(0);
    }

    public Usuario salvar(Usuario usuario) {
        Usuario usuarioVerificado = this.verificarPassword(usuario);
        //usuarioRepository.save(usuarioVerificado);
        usuarioRepository.saveAndFlush(usuarioVerificado);
        return usuarioVerificado;
    }

    private Usuario verificarPassword(Usuario usuario) {
        Object password = usuario.getPassword();
        Object passwordConfirmacao = usuario.getConfirmPassword();

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
