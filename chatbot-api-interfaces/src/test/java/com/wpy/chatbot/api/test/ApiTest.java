package com.wpy.chatbot.api.test;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

public class ApiTest {
    @Test
    public void query_unanswered_question() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/48411118851818/topics?scope=unanswered_questions&count=20");

        get.addHeader("cookie","zsxq_access_token=4A09BCCE-C08B-9B1E-3862-32F1AC28D635_A6F5CD64C22A0DDC; sensorsdata2015jssdkcross={\"distinct_id\":\"582148128528244\",\"first_id\":\"1822fb5950d1cb-0a83f7da5ae1bb-2c2d1f64-1484784-1822fb5950e17b5\",\"props\":{\"$latest_traffic_source_type\":\"引荐流量\",\"$latest_search_keyword\":\"未取到值\",\"$latest_referrer\":\"https://www.code-nav.cn/\"},\"identities\":\"eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTgyMmZiNTk1MGQxY2ItMGE4M2Y3ZGE1YWUxYmItMmMyZDFmNjQtMTQ4NDc4NC0xODIyZmI1OTUwZTE3YjUiLCIkaWRlbnRpdHlfbG9naW5faWQiOiI1ODIxNDgxMjg1MjgyNDQifQ==\",\"history_login_id\":{\"name\":\"$identity_login_id\",\"value\":\"582148128528244\"},\"$device_id\":\"1822fb5950d1cb-0a83f7da5ae1bb-2c2d1f64-1484784-1822fb5950e17b5\"}; abtest_env=beta");
        get.addHeader("Content-Type","application/json;charset=utf8");

        CloseableHttpResponse response = httpClient.execute(get);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        }
    }

    @Test
    public void answer() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/412884248251548/answer");

        post.addHeader("cookie","zsxq_access_token=4A09BCCE-C08B-9B1E-3862-32F1AC28D635_A6F5CD64C22A0DDC; sensorsdata2015jssdkcross={\"distinct_id\":\"582148128528244\",\"first_id\":\"1822fb5950d1cb-0a83f7da5ae1bb-2c2d1f64-1484784-1822fb5950e17b5\",\"props\":{\"$latest_traffic_source_type\":\"引荐流量\",\"$latest_search_keyword\":\"未取到值\",\"$latest_referrer\":\"https://www.code-nav.cn/\"},\"identities\":\"eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTgyMmZiNTk1MGQxY2ItMGE4M2Y3ZGE1YWUxYmItMmMyZDFmNjQtMTQ4NDc4NC0xODIyZmI1OTUwZTE3YjUiLCIkaWRlbnRpdHlfbG9naW5faWQiOiI1ODIxNDgxMjg1MjgyNDQifQ==\",\"history_login_id\":{\"name\":\"$identity_login_id\",\"value\":\"582148128528244\"},\"$device_id\":\"1822fb5950d1cb-0a83f7da5ae1bb-2c2d1f64-1484784-1822fb5950e17b5\"}; abtest_env=beta");
        post.addHeader("Content-Type","application/json;charset=utf8");

        String paramJson = "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"自己去百度！\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"silenced\": false\n" +
                "  }\n" +
                "}";

        StringEntity entity = new StringEntity(paramJson, ContentType.create("text/json","UTF-8"));
        post.setEntity(entity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        }
    }

    @Test
    public void test_chatGpt() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.openai.com/v1/completions");
        post.addHeader("Content-Type","application/json");
        post.addHeader("Authorization","Bearer ");

        String paramJson = "";

        StringEntity entity = new StringEntity(paramJson, ContentType.create("text/json","UTF-8"));
        post.setEntity(entity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        }
    }

}
