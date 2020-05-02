package com.thales.brewer.service.event.cerveja;

import com.thales.brewer.model.Cerveja;
import org.springframework.util.StringUtils;

public class CervejaSalvaEvent {

    private Cerveja cerveja;

    public CervejaSalvaEvent(Cerveja cerveja) {
        this.cerveja = cerveja;
    }

    public Cerveja getCerveja(){
        return cerveja;
    }

    public boolean isFoto(){
        return !StringUtils.isEmpty(cerveja.getFoto());
    }

}
