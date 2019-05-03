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
        System.out.println("teste1  " + cliente.getId());
        System.out.println("teste1  " + cliente.getPassword());
        if (cliente.getId() != null && cliente.getPassword() == null) {
            Cliente c = clienteRepository.findById(cliente.getId()).get();
            System.out.println("teste3  " + c.getId());
            System.out.println("teste3  " + c.getPassword());
            cliente.setPassword(c.getPassword());
            cliente.setConfirmPassword(c.getPassword());
        }
        System.out.println("teste2  " + cliente.getId());
        System.out.println("teste2  " + cliente.getPassword());
        System.out.println("teste2  " + cliente.getConfirmPassword());
        return clienteRepository.save(cliente);
    }


}
