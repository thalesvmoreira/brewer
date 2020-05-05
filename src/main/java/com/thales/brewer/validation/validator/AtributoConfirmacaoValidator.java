package com.thales.brewer.validation.validator;

import com.thales.brewer.validation.AtributoConfirmacao;
import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AtributoConfirmacaoValidator implements ConstraintValidator<AtributoConfirmacao, Object> {

    private String atributo;

    private String atributoConfirmacao;

    @Override
    public void initialize(AtributoConfirmacao constraintAnnotation) {
        this.atributo = constraintAnnotation.atributo();
        this.atributoConfirmacao = constraintAnnotation.atributoConfirmacao();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {

        boolean valido = false;

        try {
            Object valorAtributo = BeanUtils.getProperty(o, this.atributo);
            Object valorAtributoConfirmacao = BeanUtils.getProperty(o, this.atributoConfirmacao);

            valido = ambosSaoNulos(valorAtributo, valorAtributoConfirmacao) || ambosSaoIguais(valorAtributo, valorAtributoConfirmacao);
        } catch(Exception e) {
            throw new RuntimeException("Erro recuperando valores dos atributos", e);
        }

        if(!valido){
            constraintValidatorContext.disableDefaultConstraintViolation();
            String mensagem = constraintValidatorContext.getDefaultConstraintMessageTemplate();

            ConstraintValidatorContext.ConstraintViolationBuilder violationBuilder =
                    constraintValidatorContext.buildConstraintViolationWithTemplate(mensagem);

            violationBuilder.addPropertyNode(atributoConfirmacao).addConstraintViolation();
        }

        return valido;
    }

    private boolean ambosSaoIguais(Object valorAtributo, Object valorAtributoConfirmacao) {
        return valorAtributo != null && valorAtributo.equals(valorAtributoConfirmacao);
    }

    private boolean ambosSaoNulos(Object valorAtributo, Object valorAtributoConfirmacao) {
        return valorAtributo == null && valorAtributoConfirmacao == null;
    }
}
