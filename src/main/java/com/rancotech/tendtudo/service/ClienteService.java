package com.rancotech.tendtudo.service;

import com.rancotech.tendtudo.model.Cliente;
import com.rancotech.tendtudo.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvar(Cliente cliente) {
        if (cliente.getId() != null && cliente.getPassword() == null) {
            Cliente c = clienteRepository.findById(cliente.getId()).get();
            cliente.setPassword(c.getPassword());
            cliente.setConfirmPassword(c.getPassword());
        }
        return clienteRepository.save(cliente);
    }


}
