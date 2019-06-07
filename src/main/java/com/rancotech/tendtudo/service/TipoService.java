package com.rancotech.tendtudo.service;

import com.rancotech.tendtudo.model.Tipo;
import com.rancotech.tendtudo.model.enumerated.StatusAtivo;
import com.rancotech.tendtudo.repository.TipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoService {

    @Autowired
    private TipoRepository tipoRepository;

    public Tipo salvar(Tipo tipo) {
        tipo.setAtivo(StatusAtivo.ATIVADO);
        Tipo tipoSalvo = tipoRepository.saveAndFlush(tipo);
        return tipoSalvo;
    }

    public Tipo remove(Long id, Pageable pageable) {
        List<Tipo> tipos = tipoRepository.selectTipoNextPage(pageable.getPageSize(), pageable.getPageNumber(), StatusAtivo.ATIVADO.ordinal());
        Optional<Tipo> tipo = tipoRepository.findById(id);
        if (tipo.isPresent()) {
            tipo.get().setAtivo(StatusAtivo.DESATIVADO);
            tipoRepository.saveAndFlush(tipo.get());
        }
        return tipos.isEmpty() ? null : tipos.get(0);
    }

}
