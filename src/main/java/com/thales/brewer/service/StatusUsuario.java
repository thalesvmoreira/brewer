package com.thales.brewer.service;

import com.thales.brewer.repository.Usuarios;

public enum StatusUsuario {

    ATIVAR{
        @Override
        public void executar(Long[] codigos, Usuarios usuarios){
            usuarios.findByIdIn(codigos).forEach(u -> u.setAtivo(true));
        }
    },
    DESATIVAR{
        @Override
        public void executar(Long[] codigos, Usuarios usuarios){
            usuarios.findByIdIn(codigos).forEach(u -> u.setAtivo(false));
        }
    };

    public abstract void executar(Long[] codigos, Usuarios usuarios);
}
