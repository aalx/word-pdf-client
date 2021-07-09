package com.cssca.convert;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class FileConvert extends Thread{
    private String serverUrl;
    private String sourceFile;
    private String destFile;
    public FileConvert(String serverUrl,String sourceFile,String destFile){
        this.serverUrl=serverUrl;
        this.sourceFile=sourceFile;
        this.destFile=destFile;
    }

    public void run(){
        try {
            this.word2Pdf(this.sourceFile, this.destFile);
        }catch (Exception e){
            System.out.println(this.sourceFile+"---转换失败。"+e.getMessage());
        }
    }


    private void word2Pdf(String sourceFile,String destFile) throws Exception{
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            File inputFile = new File(sourceFile);
            if (!inputFile.exists()) {
                throw new FileNotFoundException(sourceFile + "  File not exsit.");
            }

            // 如果目标路径不存在, 则新建该路径
            File outputFile = new File(destFile);
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }


            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(480000).setConnectionRequestTimeout(480000).setSocketTimeout(480000).build();
            HttpPost post = new HttpPost(serverUrl + "/word2Pdf_documents4j");
            post.setConfig(requestConfig);
            HttpEntity entity = new FileEntity(inputFile);
            post.setEntity(entity);
            HttpResponse response = httpClient.execute(post);
            //获取Http响应的码 200
            int startCode = response.getStatusLine().getStatusCode();
            System.out.println(startCode);
            if (startCode == 200) {
               try(InputStream inputStream = response.getEntity().getContent();
                OutputStream outputStream = new FileOutputStream(outputFile)) {
                   byte[] buf = new byte[1024];
                   int len;
                   while ((len = inputStream.read(buf)) > 0) {
                       outputStream.write(buf, 0, len);
                   }
               }catch (Exception e){
//                   e.printStackTrace();
                   System.out.println("保存PDF失败，"+e.getMessage());
               }
               System.out.println(outputFile.getAbsoluteFile()+"  保存成功。");
            } else {
                httpClient.close();
                throw new RuntimeException("服务器响应错误！code:" + startCode);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


//    public static void main(String[] args){
//        for(int i=1;i<96;i++){
////            FileConvert fileConvert=new FileConvert("http://127.0.0.1:8080/convert-issuer","D:\\doc-2-pdf\\doc\\1 ("+i+").doc","D:\\doc-2-pdf\\pdf\\1("+i+").pdf");
//            FileConvert fileConvert=new FileConvert("http://192.168.168.220:8080/convert-issuer","D:\\doc-2-pdf\\doc\\1 ("+i+").doc","D:\\doc-2-pdf\\pdf\\1("+i+").pdf");
//            fileConvert.start();
//            try {
//                TimeUnit.MILLISECONDS.sleep(200);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }


//        try {
//            fileConvert.word2Pdf("D:\\doc-2-pdf\\doc", "D:\\doc-2-pdf\\pdf");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }


}
