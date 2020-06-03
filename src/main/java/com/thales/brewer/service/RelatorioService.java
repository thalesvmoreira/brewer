package com.thales.brewer.service;

import com.thales.brewer.dto.PeriodoRelatorio;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class RelatorioService {

    @Autowired
    private DataSource dataSource;

    @Value("${spring.mvc.locale}")
    private Locale locale;

    public byte[] gerarRelatorioVendasEmitidas(PeriodoRelatorio periodoRelatorio) throws Exception {
        Date dataInicio = Date.from(LocalDateTime.of(periodoRelatorio.getDataInicio(), LocalTime.of(0, 0, 0))
                .atZone(ZoneId.systemDefault()).toInstant());
        Date dataFim = Date.from(LocalDateTime.of(periodoRelatorio.getDataFim(), LocalTime.of(23, 59, 59))
                .atZone(ZoneId.systemDefault()).toInstant());

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("format", "pdf");
        parametros.put("data_inicio", dataInicio);
        parametros.put("data_fim", dataFim);
        parametros.put(JRParameter.REPORT_LOCALE, locale);

        InputStream inputStream = this.getClass()
                .getResourceAsStream("/relatorios/relatorio_vendas_emitidas.jasper");

        Connection con = this.dataSource.getConnection();

        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, con);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } finally {
            con.close();
        }
    }

}
