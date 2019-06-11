package com.rancotech.tendtudo.service;

import com.rancotech.tendtudo.model.Cliente;
import com.rancotech.tendtudo.model.enumerated.StatusAtivo;
import com.rancotech.tendtudo.repository.ClienteRepository;
import com.rancotech.tendtudo.service.exception.SenhaConfirmacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente remover(Long id, Pageable pageable) {
        List<Cliente> cliente = clienteRepository.selectClienteNextPage(pageable.getPageSize(), pageable.getPageNumber(), StatusAtivo.ATIVADO.ordinal());
        Optional<Cliente> clienteRemove = clienteRepository.findById(id);
        if (clienteRemove.isPresent()) {
            clienteRemove.get().setAtivo(StatusAtivo.DESATIVADO);
            clienteRepository.saveAndFlush(clienteRemove.get());
        }

        return cliente.isEmpty() ? null : cliente.get(0);
    }

    public Cliente salvar(Cliente cliente) {
        /*if (cliente.getId() != null && cliente.getPassword() == null) {
            Cliente c = clienteRepository.findById(cliente.getId()).get();
            cliente.setPassword(c.getPassword());
            cliente.setConfirmPassword(c.getPassword());
        }*/

        Cliente clienteVerificado = this.verificarPassword(cliente);
        clienteVerificado.setAtivo(StatusAtivo.ATIVADO);
        return clienteRepository.saveAndFlush(clienteVerificado);
    }

    private Cliente verificarPassword(Cliente cliente) {
        Object password = cliente.getPassword();
        Object passwordConfirmacao = cliente.getConfirmPassword();

        if (((String) password).isEmpty() || cliente.getPassword() == null) {
            cliente.setPassword(null);
        } else if (password.equals(passwordConfirmacao)) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            cliente.setPassword(encoder.encode(cliente.getPassword()));
            cliente.setConfirmado(true);
        } else {
            throw new SenhaConfirmacaoException();
        }
        return cliente;
    }

}
