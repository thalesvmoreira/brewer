package com.thales.brewer.controller.converter;

import com.thales.brewer.model.Estilo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class EstiloConverter implements Converter<String, Estilo> {

    @Override
    public Estilo convert(String codigo) {
        if(!StringUtils.isEmpty(codigo)) {
            Estilo estilo = new Estilo();
            estilo.setId(Long.valueOf(codigo));
            return estilo;
        }

        return null;
    }
}
