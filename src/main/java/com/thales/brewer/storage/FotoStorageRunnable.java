package com.thales.brewer.storage;

import com.thales.brewer.dto.FotoDTO;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

public class FotoStorageRunnable implements Runnable {

    private MultipartFile[] files;
    private DeferredResult<FotoDTO> resultado;
    private FotoStorage fotoStorage;

    public FotoStorageRunnable(MultipartFile[] files, DeferredResult<FotoDTO> resultado, FotoStorage fotoStorage) {
        this.files = files;
        this.resultado = resultado;
        this.fotoStorage = fotoStorage;
    }

    @Override
    public void run(){
        String nomeFoto = this.fotoStorage.salvarTemporariamente(files);
        String contentType = files[0].getContentType();

        resultado.setResult(new FotoDTO(nomeFoto, contentType));
    }
}
