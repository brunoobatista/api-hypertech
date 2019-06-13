package com.rancotech.tendtudo.service;

import com.rancotech.tendtudo.model.Cliente;
import com.rancotech.tendtudo.model.Produto;
import com.rancotech.tendtudo.model.Venda;
import com.rancotech.tendtudo.model.VendaProduto;
import com.rancotech.tendtudo.model.enumerated.StatusAtivo;
import com.rancotech.tendtudo.repository.ClienteRepository;
import com.rancotech.tendtudo.repository.ProdutoRepository;
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

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Venda salvar(Venda venda) {
        System.out.println("TESTE 1  #####");
        Long id = venda.getClienteId();
        System.out.println("TESTE 2  #####");
        this.calculaValorTotalVenda(venda);
        System.out.println("TESTE 3  #####");
        if (id != null) {
            if (venda.getCliente() == null || (venda.getCliente() != null && venda.getCliente().getId() != id)) {
                Optional<Cliente> cliente = clienteRepository.findById(id);
                venda.setCliente(cliente.get());
            }
        }
        System.out.println("TESTE 4  #####");

        venda.setAtivo(StatusAtivo.ATIVADO);
        System.out.println("TESTE 5  #####");
        this.vendaRepository.save(venda);
        System.out.println("TESTE 6  #####");
        for (VendaProduto vp : venda.getProdutos()) {
            VendaProduto vendaProduto;
            if (vp.getId() == null) {
                vendaProduto = new VendaProduto(venda, vp.getProduto(), vp.getQuantidade());
                this.setReservaVendaNova(vp.getProduto(), vendaProduto);
            } else {
                vendaProduto = vendaProdutoRepository.findByVendaIdAndProdutoId(venda.getId(), vp.getProduto().getId()).get();
                this.setReservaVendaExistente(vp.getProduto(), vendaProduto, vp);
                vendaProduto.setQuantidade(vp.getQuantidade());
            }
            VendaProduto vpSalvo = vendaProdutoRepository.save(vendaProduto);
            vp.setId(vpSalvo.getId());
        }
        System.out.println("TESTE 7  #####");
        return venda;
    }

    @Transactional
    public void finalizar(Venda venda) {
        venda.setAtivo(StatusAtivo.ATIVADO);
        for (VendaProduto vp: venda.getProdutos()) {
            VendaProduto vendaProduto = vendaProdutoRepository.findByVendaIdAndProdutoId(venda.getId(), vp.getProduto().getId()).get();

            Produto produto = vendaProduto.getProduto();
            produto.setReserva(Math.subtractExact(produto.getReserva(), vendaProduto.getQuantidade()));
            produto.setEstoque(Math.subtractExact(produto.getEstoque(), vendaProduto.getQuantidade()));
            produtoRepository.saveAndFlush(produto);
        }
        vendaRepository.saveAndFlush(venda);
    }

    @Transactional
    public void cancelar(Venda venda) {
        venda.setAtivo(StatusAtivo.ATIVADO);
        for (VendaProduto vp: venda.getProdutos()) {
            VendaProduto vendaProduto = vendaProdutoRepository.findByVendaIdAndProdutoId(venda.getId(), vp.getProduto().getId()).get();

            Produto produto = vendaProduto.getProduto();
            produto.setReserva(Math.subtractExact(produto.getReserva(), vendaProduto.getQuantidade()));
            produtoRepository.saveAndFlush(produto);
        }
        vendaRepository.saveAndFlush(venda);
    }

    @Transactional
    public void estornar(Venda venda) {
        venda.setAtivo(StatusAtivo.ATIVADO);
        for (VendaProduto vp: venda.getProdutos()) {
            VendaProduto vendaProduto = vendaProdutoRepository.findByVendaIdAndProdutoId(venda.getId(), vp.getProduto().getId()).get();

            Produto produto = vendaProduto.getProduto();
            produto.setEstoque(Math.addExact(produto.getEstoque(), vendaProduto.getQuantidade()));
            produtoRepository.saveAndFlush(produto);
        }
        vendaRepository.saveAndFlush(venda);
    }

    @Transactional
    public Venda removerProduto(Long vendaId, Long produtoId) {
        Optional<Venda> venda = vendaRepository.findById(vendaId);
        Optional<VendaProduto> vendaProduto = vendaProdutoRepository.findByVendaIdAndProdutoId(venda.get().getId(), produtoId);

        this.removeProdutoDaLista(venda.get(), vendaProduto.get());

        vendaProdutoRepository.deleteByVendaIdAndProdutoId(venda.get().getId(), produtoId);

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
    }

    public Optional<Venda> findByIdAtivo(Long id) {
        Optional<Venda> venda = this.vendaRepository.findByIdAndAtivoEquals(id, StatusAtivo.ATIVADO);

        if (venda.get().getClienteId() != null) {
            Optional<Cliente> cliente = this.clienteRepository.findById(venda.get().getClienteId());
            venda.get().setCliente(cliente.get());
        }
        return venda;
    }

    private void setReservaVendaExistente(Produto produto, VendaProduto vendaProduto, VendaProduto novaVendaProduto) {
        produto.setReserva(Math.subtractExact(produto.getReserva(), vendaProduto.getQuantidade()));
        this.setReservaVendaNova(produto, novaVendaProduto);
    }

    private void setReservaVendaNova(Produto produto, VendaProduto vendaProduto) {
        produto.setReserva(Math.addExact(produto.getReserva(), vendaProduto.getQuantidade()));
        produtoRepository.saveAndFlush(produto);
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
        Produto produto = vendaProduto.getProduto();
        produto.setReserva(Math.subtractExact(produto.getReserva(), vendaProduto.getQuantidade()));
        produtoRepository.saveAndFlush(produto);
        venda.getProdutos().remove(vendaProduto);
    }

}
