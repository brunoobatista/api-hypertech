package com.rancotech.tendtudo.model;

import com.rancotech.tendtudo.model.embeddables.VendaProdutoId;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "venda_produtos")
public class VendaProduto {

    @EmbeddedId
    private VendaProdutoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idVenda")
    private Venda venda;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idProduto")
    private Produto produto;

    @Column(name = "quantidade")
    private Integer quantidade;

    private VendaProduto() {}

    public VendaProduto(
            Venda venda, Produto produto, Integer quantidade
    ) {
        this.quantidade = quantidade;
        this.venda = venda;
        this.produto = produto;
        this.id = new VendaProdutoId(venda.getId(), produto.getId());
    }

    public VendaProdutoId getId() {
        return id;
    }

    public void setId(VendaProdutoId id) {
        this.id = id;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VendaProduto that = (VendaProduto) o;
        return Objects.equals(venda, that.venda) &&
                Objects.equals(produto, that.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(venda, produto);
    }
}
