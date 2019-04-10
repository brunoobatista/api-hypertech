package com.rancotech.tendtudo.repository.venda;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class VendaRepositoryImpl implements VendaRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

}
