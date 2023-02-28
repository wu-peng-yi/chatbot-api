package com.wpy.chatbot.api.domain.ai;

import java.io.IOException;

/**
 * @author wupengyi
 */
public interface IOpenAI {

    String doChatGPT(String question) throws IOException;
}
