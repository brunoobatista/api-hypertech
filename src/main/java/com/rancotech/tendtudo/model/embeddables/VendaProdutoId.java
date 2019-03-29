package com.rancotech.tendtudo.model.embeddables;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class VendaProdutoId implements Serializable {

    @Column(name = "id_venda")
    private Long idVenda;

    @Column(name = "id_produto")
    private Long idProduto;

    private VendaProdutoId() {}

    public VendaProdutoId(
            Long idVenda,
            Long idProduto
    ) {
        this.idVenda = idVenda;
        this.idProduto = idProduto;
    }

    public Long getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(Long idVenda) {
        this.idVenda = idVenda;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VendaProdutoId that = (VendaProdutoId) o;
        return Objects.equals(idVenda, that.idVenda) &&
                Objects.equals(idProduto, that.idProduto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVenda, idProduto);
    }
}
