package com.thales.brewer.controller.converter;

import com.thales.brewer.model.Cidade;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class CidadeConverter implements Converter<String, Cidade> {

    @Override
    public Cidade convert(String codigo) {
        if(!StringUtils.isEmpty(codigo)) {
            Cidade cidade = new Cidade();
            cidade.setId(Long.valueOf(codigo));
            return cidade;
        }

        return null;
    }
}