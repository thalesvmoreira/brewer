package com.thales.brewer.controller.converter;

import com.thales.brewer.model.Grupo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class GrupoConverter implements Converter<String, Grupo> {

    @Override
    public Grupo convert(String codigo) {
        if(!StringUtils.isEmpty(codigo)) {
            Grupo grupo = new Grupo();
            grupo.setId(Long.valueOf(codigo));
            return grupo;
        }

        return null;
    }

}
