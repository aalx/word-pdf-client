package com.cssca.convert;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Word2PdfApplication implements CommandLineRunner {
    @Value("${file.conertServerUrl}")
    private String conertServerUrl;
    @Value("${file.sourceFilePath}")
    private String sourceFilePath;
    @Value("${file.destFilePath}")
    private String destFilePath;

    public static void main(String[] args) throws Exception {

        SpringApplication.run(Word2PdfApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        for(int i=1;i<96;i++){
//            FileConvert fileConvert=new FileConvert("http://127.0.0.1:8080/convert-issuer","D:\\doc-2-pdf\\doc\\1 ("+i+").doc","D:\\doc-2-pdf\\pdf\\1("+i+").pdf");
            FileConvert fileConvert=new FileConvert(conertServerUrl,sourceFilePath+ File.separator+"1 ("+i+").doc",destFilePath+ File.separator+"1("+i+").pdf");
            fileConvert.start();
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        Thread.currentThread().join();
    }
}
