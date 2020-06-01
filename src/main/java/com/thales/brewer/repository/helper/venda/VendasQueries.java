package com.thales.brewer.repository.helper.venda;

import com.thales.brewer.dto.VendaMes;
import com.thales.brewer.model.Venda;
import com.thales.brewer.repository.filter.VendaFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface VendasQueries {
    public Page<Venda> filtrar(VendaFilter filtro, Pageable pageable);

    public Venda buscarComItens(Long id);

    public BigDecimal valorTotalNoAno();

    public BigDecimal valorTotalNoMes();

    public BigDecimal valorTicketMedioNoAno();

    public List<VendaMes> totalPorMes();
}
