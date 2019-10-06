package com.gabrielferreira.aplicativo;

import com.gabrielferreira.aplicativo.services.AmazonS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AplicativoApplication implements CommandLineRunner {

    @Autowired
    private AmazonS3Service amazonS3Service;

    public static void main(String[] args) {
        SpringApplication.run(AplicativoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        amazonS3Service.uploadArquivo("C:\\Users\\gabriel\\Pictures\\cp1.jpg");
    }
}
