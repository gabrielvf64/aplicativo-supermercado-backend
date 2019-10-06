package com.gabrielferreira.aplicativo.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class AmazonS3Service {

    private Logger logger = LoggerFactory.getLogger(AmazonS3Service.class);

    @Autowired
    private AmazonS3 s3client;

    @Value("${s3.bucket}")
    private String nomeBucket;

    public void uploadArquivo(String caminhoArquivoLocal) {
        try {
            File arquivo = new File(caminhoArquivoLocal);
            logger.info("Iniciando o upload...");
            s3client.putObject(new PutObjectRequest(nomeBucket, "teste.jpg", arquivo));
            logger.info("Arquivo enviado com sucesso.");
        } catch (AmazonServiceException e) {
            logger.info("AmazonServiceException: " + e.getErrorMessage());
            logger.info("Status code: " + e.getErrorCode());
        } catch (AmazonClientException e) {
            logger.info("AmazonClientException: " + e.getMessage());
        }
    }
}
