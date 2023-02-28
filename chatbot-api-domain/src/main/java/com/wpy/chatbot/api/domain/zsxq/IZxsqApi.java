package com.wpy.chatbot.api.domain.zsxq;

import com.wpy.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;

import java.io.IOException;

/**
 * @author wupengyi
 */
public interface IZxsqApi {

    /**
     *
     * @param groupId
     * @param cookie
     * @throws IOException
     */
    UnAnsweredQuestionsAggregates queryUnAnsweredQuestionsTopicId(String groupId, String cookie) throws IOException;

    boolean answer(String groupId, String cookie,String topicId,String text,boolean silenced) throws IOException;
}
