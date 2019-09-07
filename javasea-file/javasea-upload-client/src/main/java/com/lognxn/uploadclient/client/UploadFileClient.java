package com.lognxn.uploadclient.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName UploadFileClient
 * @Description 读取本地目录和子目录下文件,通过httpclient上传到服务器
 * @Author longxiaonan@163.com
 * @Date 2018/8/12 18:15
 */
@Slf4j
public class UploadFileClient {
    private static int count = 0;
    private static double sizeSum = 0.0D;
    private static double minSize = Double.MAX_VALUE;
    private static long minTime = Long.MAX_VALUE;
    private static long maxTime = 0;
    private static double maxSize = 0;

    private static double minSize1 = Double.MAX_VALUE;
    private static long minTime1 = Long.MAX_VALUE;
    private static long maxTime1 = 0;
    private static double maxSize1 = 0;


    private static String uploadUri = "http://192.168.3.18:8080/file-upload";

    public static void main(String[] args) throws IOException {
//        uploadTest();
//        fileFilter();

        //指定本地需要上传的目录
//        File dir = new File("C:\\jiyi\\test");
        File dir = new File("C:\\tmp\\智瑞数据样本（20180728pm）");
        long start = System.currentTimeMillis();
        uploadFile(dir, 0);
        long end = System.currentTimeMillis();
        long cost = end - start;
        double costPer = cost * 1.0 / count;  //每张花费
        double sizePer = sizeSum / count;   //每张大小
        log.info("一共上传" + count + "张图片, 总费时" + cost + "毫秒, 平均每张花费为" + costPer + "毫秒, 平均每张大小为" + sizePer + "K");
        log.info("最大的图片为:"+ (maxSize) +"K, 花费"+maxTime+"毫秒");
        log.info("最小的图片为:"+ (minSize) +"K, 花费"+minTime+"毫秒");
        log.info("最长的上传时间:"+ (maxTime1) +"毫秒, 图片大小为"+maxSize1+"K");
        log.info("最短的上传时间:"+ (minTime1) +"毫秒, 图片大小为"+minSize1+"K");
    }

    /**
     * @Description 上传指定目录下的文件, 如果存在子目录递归遍历后上传
     * @Author longxiaonan@163.com
     **/
    public static void uploadFile(File dir, int lev) {
        File[] subFiles = dir.listFiles();
        for (File subFile : subFiles) {
            if(subFile.isFile()){
                long starTimePer = System.currentTimeMillis();
                count ++;
                double size = subFile.length() * 1.0 / 1024;

                try {
                    //如果是文件, 进行上传操作
                    upload2(subFile.getAbsolutePath());
                } catch (IOException e) {
                }
                long endTimePer = System.currentTimeMillis();
                long costPer = endTimePer - starTimePer;
                if(size <= minSize){
                    minSize = size;
                    minTime = costPer;
                }
                if(size >= maxSize){
                    maxSize = size;
                    maxTime = costPer;
                }
                sizeSum += size;
                if(costPer <= minTime1){
                    minTime1 = costPer;
                    minSize1 = size;
                }
                if(costPer >= maxTime1){
                    maxTime1 = costPer;
                    maxSize1 = size;
                }
            }
            if (subFile.isDirectory()) {
                uploadFile(subFile, lev + 1);
            }
        }

    }

    private static void fileFilter() {
        File file = new File("C:\\jiyi");
        String[] filePaths = file.list(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                File file = new File(dir, name);
                if (file.isFile())
                    return true;
                return false;
            }
        });

//        System.out.println(file.length() * 1.0 / 1024);
        Arrays.asList(filePaths).forEach((a) -> {
            System.out.println(a);
        });
    }

    public static void upload2(String filePath) throws ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse httpResponse = null;
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000000).build();
        HttpPost httpPost = new HttpPost(uploadUri);
        httpPost.setConfig(requestConfig);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        //multipartEntityBuilder.setCharset(Charset.forName("UTF-8"));

        //File file = new File("F:\\Ken\\1.PNG");
        //FileBody bin = new FileBody(file);

        File file = new File(filePath);

        //multipartEntityBuilder.addBinaryBody("file", file,ContentType.create("image/png"),"abc.pdf");
        //当设置了setSocketTimeout参数后，以下代码上传PDF不能成功，将setSocketTimeout参数去掉后此可以上传成功。上传图片则没有个限制
        //multipartEntityBuilder.addBinaryBody("file",file,ContentType.create("application/octet-stream"),"abd.pdf");
        multipartEntityBuilder.addBinaryBody("file", file);
        //multipartEntityBuilder.addPart("comment", new StringBody("This is comment", ContentType.TEXT_PLAIN));
        multipartEntityBuilder.addTextBody("comment", "this is comment");
        HttpEntity httpEntity = multipartEntityBuilder.build();
        httpPost.setEntity(httpEntity);

        httpResponse = httpClient.execute(httpPost);
        HttpEntity responseEntity = httpResponse.getEntity();
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(responseEntity.getContent()));
            StringBuffer buffer = new StringBuffer();
            String str = "";
            while (StringUtils.isNotBlank(str = reader.readLine())) {
                buffer.append(str);
            }

            System.out.println(buffer.toString());
        }

        httpClient.close();
        if (httpResponse != null) {
            httpResponse.close();
        }

    }

    public static void upload() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        try {
            HttpPost httppost = new HttpPost("http://localhost:8080/WEY.WebApp/auth/right/right/receiveFile.html");

            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000).build();
            httppost.setConfig(requestConfig);

            FileBody bin = new FileBody(new File("F:\\Ken\\abc.pdf"));
            StringBody comment = new StringBody("This is comment", ContentType.TEXT_PLAIN);

            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("file", bin).addPart("comment", comment).build();

            httppost.setEntity(reqEntity);

            System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    String responseEntityStr = EntityUtils.toString(response.getEntity());
                    System.out.println(responseEntityStr);
                    System.out.println("Response content length: " + resEntity.getContentLength());
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @Description 普通POST请求
     * @Author longxiaonan@163.com
     * @Date 18:24 2018/8/12
     **/
    public String post() throws ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse httpResponse = null;
        HttpPost httpPost = new HttpPost("http://localhost:8080/WEY.WebApp/auth/right/right/receivePost.html");
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(20000).setSocketTimeout(22000).build();
        httpPost.setConfig(requestConfig);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user.loginId", "Lin"));
        params.add(new BasicNameValuePair("user.employeeName", "令狐冲"));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, Charset.forName("UTF-8"));
        httpPost.setEntity(entity);
        httpResponse = httpClient.execute(httpPost);
        HttpEntity responseEntity = httpResponse.getEntity();
        if (responseEntity != null) {
            String content = EntityUtils.toString(responseEntity, "UTF-8");
            System.out.println(content);
        }

        if (httpResponse != null) {
            httpResponse.close();
        }
        if (httpClient != null) {
            httpClient.close();
        }
        return null;
    }
}
