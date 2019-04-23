package com.rancotech.tendtudo.model.embeddables;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class VendaProdutoId implements Serializable {

    @Column(name = "venda_id", insertable = false, updatable = false)
    private Long vendaId;

    @Column(name = "produto_id", insertable = false, updatable = false)
    private Long produtoId;

    public VendaProdutoId() {}

    public VendaProdutoId(
            Long vendaId,
            Long produtoId
    ) {
        this.vendaId = vendaId;
        this.produtoId = produtoId;
    }

    public Long getVendaId() {
        return vendaId;
    }

    public void setVendaId(Long vendaId) {
        this.vendaId = vendaId;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VendaProdutoId that = (VendaProdutoId) o;
        return Objects.equals(vendaId, that.vendaId) &&
                Objects.equals(produtoId, that.produtoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vendaId, produtoId);
    }
}
