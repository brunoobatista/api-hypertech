package com.rancotech.tendtudo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "venda")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="data_venda")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataVenda;

    @Column(name="valor")
    private BigDecimal valor;

    @OneToMany(
            mappedBy = "venda"
    )
    private List<VendaProduto> produtos = new ArrayList<>();

    /* RELACIONAMENTO ONE TO ONE*/
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "observacao")
    private String observacao;

    public void addProduto(Produto produto, Integer quantidade) {
        VendaProduto vendaProduto = new VendaProduto(this, produto, quantidade);

        produtos.add(vendaProduto);
        //produto.getVendas().add(vendaProduto);
    }

    public void removeProduto(Produto produto) {
        for (Iterator<VendaProduto> iterator = produtos.iterator(); iterator.hasNext();) {
            VendaProduto vendaProduto = iterator.next();
            if (vendaProduto.getVenda().equals(this) &&
                    vendaProduto.getProduto().equals(produto)) {
                iterator.remove();
                //vendaProduto.getProduto().getVendas().remove(vendaProduto);
                vendaProduto.setVenda(null);
                vendaProduto.setProduto(null);
            }
        }
    }

    public List<VendaProduto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<VendaProduto> produtos) {
        this.produtos = produtos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venda venda = (Venda) o;
        return id.equals(venda.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

