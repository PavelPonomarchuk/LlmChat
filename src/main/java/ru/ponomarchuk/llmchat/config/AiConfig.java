package ru.ponomarchuk.llmchat.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    @Value("${spring.ai.ollama.base-url:http://localhost:11434}")
    private String ollamaBaseUrl;

    @Value("${spring.ai.ollama.chat.model:qwen3b}")
    private String modelName;

    @Bean
    public ChatClient chatClient() {
        OllamaApi ollamaApi = OllamaApi.builder()
                .baseUrl(ollamaBaseUrl)
                .build();

        OllamaChatOptions options = OllamaChatOptions.builder()
                .model(modelName)
                .build();

        OllamaChatModel chatModel = OllamaChatModel.builder()
                .ollamaApi(ollamaApi)
                .options(options)
                .build();

        return ChatClient.builder(chatModel).build();
    }
}
