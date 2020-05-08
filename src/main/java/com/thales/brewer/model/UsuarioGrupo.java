package com.thales.brewer.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "usuario_grupo")
public class UsuarioGrupo {

    @EmbeddedId
    private UsuarioGrupoId id;

    public UsuarioGrupoId getId() {
        return id;
    }

    public void setId(UsuarioGrupoId id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioGrupo that = (UsuarioGrupo) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
