package com.thales.brewer.thymeleaf;

import com.thales.brewer.thymeleaf.processor.ClassForErrorAttributeTagProcessor;
import com.thales.brewer.thymeleaf.processor.MessageElementTagProcessor;
import com.thales.brewer.thymeleaf.processor.OrderElementTagProcessor;
import com.thales.brewer.thymeleaf.processor.PaginationElementTagProcessor;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import java.util.HashSet;
import java.util.Set;

public class BrewerDialect extends AbstractProcessorDialect {

    public BrewerDialect() {
        super("AlgaWorks Brewer", "brewer", StandardDialect.PROCESSOR_PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        final Set<IProcessor> processadores = new HashSet<>();
        processadores.add(new ClassForErrorAttributeTagProcessor(dialectPrefix));
        processadores.add(new MessageElementTagProcessor(dialectPrefix));
        processadores.add(new OrderElementTagProcessor(dialectPrefix));
        processadores.add(new PaginationElementTagProcessor(dialectPrefix));

        return processadores;
    }

}