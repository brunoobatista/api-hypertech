package com.rancotech.tendtudo.model.dashboard;

import java.io.Serializable;
import java.time.LocalDate;

public class TotalVendasMes implements Serializable {

    private LocalDate dataVenda;
    private Integer total;

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
