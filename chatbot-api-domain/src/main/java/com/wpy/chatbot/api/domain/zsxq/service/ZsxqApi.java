package com.wpy.chatbot.api.domain.zsxq.service;

import com.alibaba.fastjson.JSON;
import com.wpy.chatbot.api.domain.zsxq.IZxsqApi;
import com.wpy.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import com.wpy.chatbot.api.domain.zsxq.model.req.AnswerReq;
import com.wpy.chatbot.api.domain.zsxq.model.req.ReqData;
import com.wpy.chatbot.api.domain.zsxq.model.res.AnswerRes;
import net.sf.json.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author wupengyi
 */
@Service
public class ZsxqApi implements IZxsqApi {

    private Logger logger = LoggerFactory.getLogger(ZsxqApi.class);

    @Override
    public UnAnsweredQuestionsAggregates queryUnAnsweredQuestionsTopicId(String groupId, String cookie) throws IOException {

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();) {
            HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/" + groupId + "/topics?scope=unanswered_questions&count=20");

            get.addHeader("cookie", cookie);
            get.addHeader("Content-Type", "application/json;charset=utf8");

            CloseableHttpResponse response = httpClient.execute(get);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String jsonStr = EntityUtils.toString(response.getEntity());
                return JSON.parseObject(jsonStr, UnAnsweredQuestionsAggregates.class);
            } else {
                System.out.println(response.getStatusLine().getStatusCode());
                logger.error("");
                throw new RuntimeException("Err Code is" + response.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            throw new RuntimeException("Server error");
        }
    }

    @Override
    public boolean answer(String groupId, String cookie, String topicId, String text, boolean silenced) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/+"+topicId+"+/answer");

        post.addHeader("cookie", cookie);
        post.addHeader("Content-Type","application/json;charset=utf8");
        // 请求的时候告诉来自于浏览器
        post.addHeader("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");

//        String paramJson = "{\n" +
//                "  \"req_data\": {\n" +
//                "    \"text\": \"自己去百度！\\n\",\n" +
//                "    \"image_ids\": [],\n" +
//                "    \"silenced\": false\n" +
//                "  }\n" +
//                "}";

        AnswerReq answerReq = new AnswerReq(new ReqData(text, silenced));
        String paramJson = JSONObject.fromObject(answerReq).toString();

        StringEntity entity = new StringEntity(paramJson, ContentType.create("text/json","UTF-8"));
        post.setEntity(entity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            AnswerRes answerRes = JSON.parseObject(jsonStr, AnswerRes.class);
            return answerRes.isSucceeded();

        } else {
            throw new RuntimeException("Err Code is" + response.getStatusLine().getStatusCode());
        }
    }
}
