package com.thales.brewer.repository.listener;

import com.thales.brewer.BrewerApplication;
import com.thales.brewer.model.Cerveja;
import com.thales.brewer.storage.FotoStorage;

import javax.persistence.PostLoad;

public class CervejaEntityListener {

    @PostLoad
    public void postLoad(final Cerveja cerveja) {
        FotoStorage fotoStorage = BrewerApplication.getBean(FotoStorage.class);

        cerveja.setUrlFoto(FotoStorage.URL + cerveja.getFotoOrMock());
        cerveja.setUrlThumbnailFoto(FotoStorage.URL + FotoStorage.THUMBNAIL_PREFIX + cerveja.getFotoOrMock());
    }
}
