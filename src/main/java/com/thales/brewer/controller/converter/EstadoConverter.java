package com.thales.brewer.controller.converter;

import com.thales.brewer.model.Estado;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class EstadoConverter implements Converter<String, Estado> {

    @Override
    public Estado convert(String codigo) {
        if(!StringUtils.isEmpty(codigo)) {
            Estado estado = new Estado();
            estado.setId(Long.valueOf(codigo));
            return estado;
        }

        return null;
    }
}