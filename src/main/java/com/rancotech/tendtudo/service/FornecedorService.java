package com.rancotech.tendtudo.service;

import com.rancotech.tendtudo.model.Fornecedor;
import com.rancotech.tendtudo.model.enumerated.StatusAtivo;
import com.rancotech.tendtudo.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public Fornecedor salvar(Fornecedor fornecedor) {
        fornecedor.setAtivo(StatusAtivo.ATIVADO);
        Fornecedor fornecedorSalvo = fornecedorRepository.saveAndFlush(fornecedor);
        return fornecedorSalvo;
    }

    public Fornecedor remover(Long id, Pageable pageable) {
        List<Fornecedor> fornecedors = fornecedorRepository.selectFornecedorNextPage(pageable.getPageSize(), pageable.getPageNumber(), StatusAtivo.ATIVADO.ordinal());
        Optional<Fornecedor> fornecedor = fornecedorRepository.findById(id);
        if (fornecedor.isPresent()) {
            fornecedor.get().setAtivo(StatusAtivo.DESATIVADO);
            fornecedorRepository.saveAndFlush(fornecedor.get());
        }
        return fornecedors.isEmpty() ? null : fornecedors.get(0);
    }

}
