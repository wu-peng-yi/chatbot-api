package com.wpy.chatbot.api.domain.ai.service;

import com.alibaba.fastjson.JSON;
import com.wpy.chatbot.api.domain.ai.IOpenAI;
import com.wpy.chatbot.api.domain.ai.model.aggregates.AIAnswer;
import com.wpy.chatbot.api.domain.ai.model.vo.Choices;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public class OpenAI implements IOpenAI {

    private Logger logger = LoggerFactory.getLogger(OpenAI.class);

    @Value("${chatbot-api.openAIKey}")
    private String openAIKey;

    @Override
    public String doChatGPT(String question) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.openai.com/v1/completions");
        post.addHeader("Content-Type","application/json");
        post.addHeader("Authorization","Bearer " + openAIKey);

        String paramJson = "";

        StringEntity entity = new StringEntity(paramJson, ContentType.create("text/json","UTF-8"));
        post.setEntity(entity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            AIAnswer aiAnswer = JSON.parseObject(jsonStr, AIAnswer.class);
            StringBuilder answers = new StringBuilder();
            for (Choices choice : aiAnswer.getChoices()) {
                answers.append(choice);
            }
            return answers.toString();
        }

        return null;
    }
}
