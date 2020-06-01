package com.thales.brewer.repository.listener;

import com.thales.brewer.model.Cerveja;
import com.thales.brewer.storage.FotoStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.persistence.PostLoad;

public class CervejaEntityListener {

    @Autowired
    private FotoStorage fotoStorage;

    @PostLoad
    public void postLoad(final Cerveja cerveja){
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        cerveja.setUrlFoto(fotoStorage.getUrl(cerveja.getFotoOrMock()));
        cerveja.setUrlThumbnailFoto(fotoStorage.getUrl(fotoStorage.THUMBNAIL_PREFIX + cerveja.getFotoOrMock()));
    }
}
