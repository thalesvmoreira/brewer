package com.thales.brewer.storage;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FotoStorage {

    public final String THUMBNAIL_PREFIX = "thumbnail.";

    public String salvar(MultipartFile[] files);

    public byte[] recuperar(String foto);

    public byte[] recuperarThumbnail(String fotoCerveja);

    public void excluir(String foto);

    public String getUrl(String foto);

    default String renomearArquivo(String nomeOriginal){
        return UUID.randomUUID().toString() + "_" + nomeOriginal;
    }
}
