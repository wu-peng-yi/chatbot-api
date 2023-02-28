package com.wpy.chatbot.api.test;

import com.wpy.chatbot.api.domain.zsxq.IZxsqApi;
import com.wpy.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRunTest {

    @Value("${chatbot-api.groupId}")
    private String groupId;
    @Value("${chatbot-api.cookie}")
    private String cookie;
    @Autowired
    private IZxsqApi zxsqApi;

    @Test
    public void test_zsxqApi() throws IOException {
        UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = zxsqApi.queryUnAnsweredQuestionsTopicId(groupId, cookie);
    }
}
