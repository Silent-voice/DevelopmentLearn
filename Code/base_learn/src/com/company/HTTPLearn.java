package com.company;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class HTTPLearn {


}

/*
    HttpResponse.BodyHandlers.ofString()
    HttpResponse.BodyHandlers.ofByteArray()
    HttpResponse.BodyHandlers.ofInputStream()

    返回：headers map类型， key是String，value是指定类型String/byte[]/InputStream
 */

class HTTPClient{
    // 全局HttpClient:
    static HttpClient httpClient = HttpClient.newBuilder().build();

    public void fun1() throws Exception {
        String url = "https://www.sina.com.cn/";
        HttpRequest request = HttpRequest.newBuilder(new URI(url))
                // 设置Header:
                .header("User-Agent", "Java HttpClient").header("Accept", "*/*")
                // 设置超时:
                .timeout(Duration.ofSeconds(5))
                // 设置版本:
                .version(HttpClient.Version.HTTP_2).build();

        // 设置返回数据类型
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//        HttpResponse<byte []> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
//        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // HTTP允许重复的Header，因此一个Header可对应多个Value:
        Map<String, List<String>> headers = response.headers().map();
        for (String header : headers.keySet()) {
            // 每个header 输出一个 value，其他value暂不处理
            System.out.println(header + ": " + headers.get(header).get(0));
        }
        // 输出body
        System.out.println(response.body().substring(0, 1024) + "...");



        String url2 = "http://www.example.com/login";
        String body = "username=bob&password=123456";
        HttpRequest request2 = HttpRequest.newBuilder(new URI(url2))
                // 设置Header:
                .header("Accept", "*/*")
                .header("Content-Type", "application/x-www-form-urlencoded")
                // 设置超时:
                .timeout(Duration.ofSeconds(5))
                // 设置版本:
                .version(HttpClient.Version.HTTP_2)
                // 使用POST并设置Body:
                .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8)).build();
        HttpResponse<String> response2 = httpClient.send(request2, HttpResponse.BodyHandlers.ofString());
        String s = response2.body();
    }
}
