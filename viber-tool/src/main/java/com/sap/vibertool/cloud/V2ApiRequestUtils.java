package com.sap.vibertool.cloud;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSONObject;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;

public class V2ApiRequestUtils {

    private static final String BASE_URL = "https://api.vmoscloud.com";
    private static final String ACCESS_KEY = "p2XuiMSjvpKgYPondISzbGX1KHTS9Y2g";
    private static final String SECRET_ACCESS_KEY = "ajsrKYuy4QReEc9NOF4o7UWJ";

    public static String sendPostRequest(String path, JSONObject params) {
        try {
            String body = params == null ? "" : params.toJSONString();
            String ts = String.valueOf(System.currentTimeMillis() / 1000);

            HttpRequest request = HttpRequest.post(BASE_URL + path);
            request.header("X-Access-Key", ACCESS_KEY);
            request.header("X-Timestamp", ts);
            request.header("X-Sign", sign(ts, path, body));
            request.header("Content-Type", "application/json");
            request.body(body);

//            HttpPost req = new HttpPost(BASE_URL + path);
//            req.setHeader("X-Access-Key", ACCESS_KEY);
//            req.setHeader("X-Timestamp", ts);
//            req.setHeader("X-Sign", sign(ts, path, body));
//            req.setHeader("Content-Type", "application/json");
//            req.setEntity(new StringEntity(body, StandardCharsets.UTF_8));

           /* try (CloseableHttpClient client = HttpClients.createDefault();
                 CloseableHttpResponse resp = client.execute(req)) {
                return EntityUtils.toString(resp.getEntity(), StandardCharsets.UTF_8);
            }*/
            return request.execute().body();
        } catch (Exception e) {
            throw new RuntimeException("V2 POST failed: " + path, e);
        }
    }

    public static String sendGetRequest(String path, JSONObject params) {
        try {
            String query = buildQuery(params);
            String ts = String.valueOf(System.currentTimeMillis() / 1000);

            HttpRequest request = HttpRequest.get(BASE_URL + path + (query.isEmpty() ? "" : "?" + query));
//            HttpGet req = new HttpGet(BASE_URL + path + (query.isEmpty() ? "" : "?" + query));
//            req.setHeader("X-Access-Key", ACCESS_KEY);
//            req.setHeader("X-Timestamp", ts);
//            req.setHeader("X-Sign", sign(ts, path, query));
//            try (CloseableHttpClient client = HttpClients.createDefault();
//                 CloseableHttpResponse resp = client.execute(req)) {
//                return EntityUtils.toString(resp.getEntity(), StandardCharsets.UTF_8);
//            }

            request.header("X-Access-Key", ACCESS_KEY);
            request.header("X-Timestamp", ts);
            request.header("X-Sign", sign(ts, path, query));
            return request.execute().body();
        } catch (Exception e) {
            throw new RuntimeException("V2 GET failed: " + path, e);
        }
    }

    private static String sign(String ts, String path, String body) throws Exception {
        String signString = SECRET_ACCESS_KEY + ts + path + body;
        byte[] raw = MessageDigest.getInstance("SHA-256")
                .digest(signString.getBytes(StandardCharsets.UTF_8));
        return String.format("%064x", new BigInteger(1, raw));
    }

    private static String buildQuery(JSONObject params) {
        if (params == null || params.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> e : params.entrySet()) {
            if (sb.length() > 0) {
                sb.append('&');
            }
            sb.append(e.getKey()).append('=').append(e.getValue());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        // POST 示例
        JSONObject body = new JSONObject();
        body.put("padCode", "AC32010601132");
        System.out.println(sendPostRequest("/vcpcloud/api/padApi/padInfo", body));

        // GET 示例
        JSONObject query = new JSONObject();
        query.put("startDate", "2026-05-01");
        query.put("endDate", "2026-05-31");
        System.out.println(sendGetRequest("/vcpcloud/api/padApi/getOrderEquipmentList", query));
    }
}
