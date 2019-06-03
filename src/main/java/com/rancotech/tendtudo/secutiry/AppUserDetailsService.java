package com.rancotech.tendtudo.secutiry;

import com.rancotech.tendtudo.model.Permissao;
import com.rancotech.tendtudo.model.Role;
import com.rancotech.tendtudo.model.Usuario;
import com.rancotech.tendtudo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByUsername(username);
        Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorretos!"));
        return new UsuarioSistema(usuario, getRoles(usuario.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getRoles(Collection<Role> roles) {
        return this.getGrantedAuthorities(this.getPermissoes(roles));
    }

    private List<String> getPermissoes(Collection<Role> roles) {
        List<String> permissaos = new ArrayList<>();
        List<Permissao> collection = new ArrayList<>();

        for (Role role: roles) {
            collection.addAll(role.getPermissaos());
        }
        for (Permissao perm: collection) {
            permissaos.add(perm.getNome());
        }
        return permissaos;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> permissaos) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String permissao : permissaos) {
            authorities.add(new SimpleGrantedAuthority(permissao));
        }
        return authorities;
    }
}

