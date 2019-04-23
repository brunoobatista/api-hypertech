package com.rancotech.tendtudo.service;

import com.rancotech.tendtudo.model.Venda;
import com.rancotech.tendtudo.model.VendaProduto;
import com.rancotech.tendtudo.repository.VendaProdutoRepository;
import com.rancotech.tendtudo.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class VendaService {

    @Autowired
    private VendaProdutoRepository vendaProdutoRepository;

    @Autowired
    private VendaRepository vendaRepository;

    @Transactional
    public Venda salvar(Venda venda) {
        Venda vendaSalvo = this.calculaValorTotalVenda(venda);

        venda.getProdutos().forEach(p -> {
            VendaProduto vendaProduto = new VendaProduto(vendaSalvo, p.getProduto(), p.getQuantidade());
            vendaProdutoRepository.save(vendaProduto);
        });

        return venda;
    }

    @Transactional
    public Venda removerProduto(Long vendaId, Long produtoId) {
        Optional<Venda> venda = this.vendaRepository.findById(vendaId);
        Optional<VendaProduto> vendaProduto = this.vendaProdutoRepository.findByVendaIdAndProdutoId(venda.get().getId(), produtoId);
        this.vendaProdutoRepository.deleteByVendaIdAndProdutoId(venda.get().getId(), produtoId);

        this.removeProdutoDaLista(venda.get(), vendaProduto.get());

        return this.calculaValorTotalVenda(venda.get());
    }

    private Venda calculaValorTotalVenda(Venda venda) {
        BigDecimal valor = new BigDecimal(0);

        for (VendaProduto p : venda.getProdutos()) {
            BigDecimal custoItem = p.getProduto().getValor().multiply(new BigDecimal(p.getQuantidade()));
            valor = valor.add(custoItem);
        }
        venda.setValor(valor);
        Venda vendaSalva =  this.vendaRepository.save(venda);
        return vendaSalva;
    }

    private void removeProdutoDaLista(Venda venda, VendaProduto vendaProduto) {
        venda.getProdutos().remove(vendaProduto);
    }

}
