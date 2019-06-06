package com.rancotech.tendtudo.repository.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class VendaFilter {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime vendaDe;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime vendaAte;

    private Long clienteId;

    public LocalDateTime getVendaDe() {
        return vendaDe;
    }

    public void setVendaDe(LocalDateTime vendaDe) {
        this.vendaDe = vendaDe;
    }

    public LocalDateTime getVendaAte() {
        return vendaAte;
    }

    public void setVendaAte(LocalDateTime vendaAte) {
        this.vendaAte = vendaAte;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
}
