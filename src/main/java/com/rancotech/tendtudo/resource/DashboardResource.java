package com.rancotech.tendtudo.resource;

import com.rancotech.tendtudo.model.Venda;
import com.rancotech.tendtudo.model.dashboard.TotalVendasMes;
import com.rancotech.tendtudo.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardResource {

    @Autowired
    private VendaRepository vendaRepository;

    @GetMapping("get_total_vendas")
    public List<TotalVendasMes> getTotalVendas() {
        return vendaRepository.getTotalVendas();
    }

}
