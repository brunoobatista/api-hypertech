package com.rancotech.tendtudo.service;

import com.rancotech.tendtudo.model.Cliente;
import com.rancotech.tendtudo.model.Venda;
import com.rancotech.tendtudo.model.VendaProduto;
import com.rancotech.tendtudo.model.enumerated.StatusAtivo;
import com.rancotech.tendtudo.repository.ClienteRepository;
import com.rancotech.tendtudo.repository.VendaProdutoRepository;
import com.rancotech.tendtudo.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class VendaService {

    @Autowired
    private VendaProdutoRepository vendaProdutoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VendaRepository vendaRepository;

    @Transactional
    public Venda salvar(Venda venda) {
        Venda vendaSalvo = this.calculaValorTotalVenda(venda);

        Long id = venda.getClienteId();

        if (id != null) {
            Optional<Cliente> cliente = clienteRepository.findById(id);
            vendaSalvo.setCliente(cliente.get());
        }

        vendaSalvo.setAtivo(StatusAtivo.ATIVADO);

        this.vendaRepository.saveAndFlush(vendaSalvo);

        venda.getProdutos().forEach(p -> {
            VendaProduto vendaProduto = new VendaProduto(vendaSalvo, p.getProduto(), p.getQuantidade());
            vendaProdutoRepository.saveAndFlush(vendaProduto);
        });

        return venda;
    }

    @Transactional
    public Venda removerProduto(Long vendaId, Long produtoId) {
        Optional<Venda> venda = this.vendaRepository.findById(vendaId);
        Optional<VendaProduto> vendaProduto = this.vendaProdutoRepository.findByVendaIdAndProdutoId(venda.get().getId(), produtoId);

        this.removeProdutoDaLista(venda.get(), vendaProduto.get());

        this.vendaProdutoRepository.deleteByVendaIdAndProdutoId(venda.get().getId(), produtoId);

        return this.calculaValorTotalVenda(venda.get());
    }

    /* Método não sendo utilizado  */
    @Transactional
    public Venda remover(Long id, Pageable pageable) {
        List<Venda> vendas = vendaRepository.selectVendaNextPage(pageable.getPageSize(), pageable.getPageNumber(), StatusAtivo.ATIVADO.ordinal());
        Optional<Venda> venda = vendaRepository.findById(id);

        if (venda.isPresent()) {
            venda.get().setAtivo(StatusAtivo.DESATIVADO);
            vendaRepository.saveAndFlush(venda.get());
        }
        return vendas.isEmpty() ? null : vendas.get(0);
        //List<VendaProduto> vendaProdutos = venda.get().getProdutos();

        /*vendaProdutos.forEach(vp -> {
            this.removerProduto(venda.get().getId(), vp.getProduto().getId());
        });*/
    }

    public Optional<Venda> findByIdAtivo(Long id) {
        Optional<Venda> venda = this.vendaRepository.findByIdAndAtivoEquals(id, StatusAtivo.ATIVADO);

        if (venda.get().getClienteId() != null) {
            Optional<Cliente> cliente = this.clienteRepository.findById(venda.get().getClienteId());
            venda.get().setCliente(cliente.get());
        }
        return venda;
    }

    private Venda calculaValorTotalVenda(Venda venda) {
        BigDecimal valor = new BigDecimal(0);

        for (VendaProduto p : venda.getProdutos()) {
            BigDecimal custoItem = p.getProduto().getValor().multiply(new BigDecimal(p.getQuantidade()));
            valor = valor.add(custoItem);
        }
        venda.setValor(valor);
        return venda;
    }

    private void removeProdutoDaLista(Venda venda, VendaProduto vendaProduto) {
        venda.getProdutos().remove(vendaProduto);
    }

}
