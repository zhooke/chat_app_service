package com.tensua.blogservice.operator.ai.facade;

import com.tensua.blogservice.data.request.ai.ChatRequest;
import com.tensua.blogservice.data.response.ai.ChatResponse;
import com.tensua.blogservice.data.system.UserBeanRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhooke
 * @since 2025/1/28 11:42
 **/
@Service
@Slf4j
public class AiFacade {

    @Resource
    private WebClient webClient;


    public String chat(String userMessage, UserBeanRequest userBeanRequest) {
        ChatRequest request = new ChatRequest(
                "deepseek-chat",
                List.of(new ChatRequest.Message("user", userMessage))
        );

        ChatResponse response = webClient.post()
                .bodyValue(request)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("API Error: " + errorBody)))
                )
                .bodyToMono(ChatResponse.class)
                .block();

        return response.getChoices().get(0).getMessage().getContent();
    }
}
