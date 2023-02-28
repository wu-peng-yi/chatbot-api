package com.wpy.chatbot.api.application.job;

import com.wpy.chatbot.api.domain.ai.IOpenAI;
import com.wpy.chatbot.api.domain.zsxq.IZxsqApi;
import com.wpy.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import com.wpy.chatbot.api.domain.zsxq.model.vo.Topics;
import com.wpy.chatbot.api.domain.zsxq.service.ZsxqApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wupengyi
 */
@EnableScheduling
@Configuration
public class ChatbotSchedule {

    private Logger logger = LoggerFactory.getLogger(ChatbotSchedule.class);

    @Value("${chatbot-api.groupId}")
    private String groupId;
    @Value("${chatbot-api.cookie}")
    private String cookie;

    @Resource
    private IZxsqApi zxsqApi;

    @Resource
    private IOpenAI openAI;


    // 表达式：cron.qqe2.com
    @Scheduled(cron = "0 0/1 * * * ?")
    public void run() {
        try {
            // 检索问题
            UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = zxsqApi.queryUnAnsweredQuestionsTopicId(groupId, cookie);
            List<Topics> topics = unAnsweredQuestionsAggregates.getResp_data().getTopics();

            if (null == topics || topics.isEmpty()) {
                logger.info("本次无待回答问题");
                return;
            }

            // 一次只回答一个问题
            Topics topic = topics.get(0);
            // ai 回答
            String answer = openAI.doChatGPT(topic.getQuestion().getText().trim());

            // 3.问题回复
            zxsqApi.answer(groupId,cookie,topic.getTopic_id(),answer,true);


        } catch (Exception e) {

        }
    }


}
