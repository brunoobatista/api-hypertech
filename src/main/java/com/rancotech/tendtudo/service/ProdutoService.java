package com.rancotech.tendtudo.service;

import com.rancotech.tendtudo.model.Produto;
import com.rancotech.tendtudo.model.enumerated.StatusAtivo;
import com.rancotech.tendtudo.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto salvar(Produto produto) {
        produto.setAtivo(StatusAtivo.ATIVADO);
        Produto produtoSalvo = produtoRepository.saveAndFlush(produto);

        return produtoSalvo;
    }

    public Produto remover(Long id, Pageable pageable) {
        List<Produto> produtos = produtoRepository.selectProdutoNextPage(pageable.getPageSize(), pageable.getPageNumber(), StatusAtivo.ATIVADO.ordinal());
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isPresent()) {
            produto.get().setAtivo(StatusAtivo.DESATIVADO);
            produtoRepository.saveAndFlush(produto.get());
        }
        return produtos.isEmpty() ? null : produtos.get(0);
    }

}
