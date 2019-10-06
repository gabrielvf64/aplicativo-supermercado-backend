package com.gabrielferreira.aplicativo.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.gabrielferreira.aplicativo.services.exceptions.FileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class AmazonS3Service {

    private Logger logger = LoggerFactory.getLogger(AmazonS3Service.class);

    @Autowired
    private AmazonS3 s3client;

    @Value("${s3.bucket}")
    private String nomeBucket;

    public URI uploadArquivo(MultipartFile multipartFile) {
        try {
            String nomeArquivo = multipartFile.getOriginalFilename();
            InputStream inputStream = multipartFile.getInputStream();
            String contentType = multipartFile.getContentType();
            return uploadArquivo(nomeArquivo, inputStream, contentType);
        } catch (IOException e) {
            throw new FileException("Erro de IO: " + e.getMessage());
        }
    }

    public URI uploadArquivo(String nomeArquivo, InputStream inputStream, String contentType) {
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(contentType);
            logger.info("Iniciando upload...");
            s3client.putObject(nomeBucket, nomeArquivo, inputStream, objectMetadata);
            logger.info("Upload concluído.");
            return s3client.getUrl(nomeBucket, nomeArquivo).toURI();
        } catch (URISyntaxException e) {
            throw new FileException("Erro ao converter URL para URI");
        }
    }
}
