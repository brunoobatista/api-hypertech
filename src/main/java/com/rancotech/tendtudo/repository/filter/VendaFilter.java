package com.rancotech.tendtudo.repository.filter;

import java.time.LocalDateTime;

public class VendaFilter {

    private LocalDateTime vendaDe;
    private LocalDateTime vendaAte;

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
}
