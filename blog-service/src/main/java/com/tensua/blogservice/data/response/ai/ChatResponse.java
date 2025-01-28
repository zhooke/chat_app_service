package com.tensua.blogservice.data.response.ai;

import lombok.Data;

import java.util.List;

/**
 * @author zhooke
 * @since 2025/1/28 11:41
 **/
@Data
public class ChatResponse {
    private List<Choice> choices;

    @Data
    public static class Choice {
        private Message message;

        @Data
        public static class Message {
            private String role;
            private String content;
        }
    }
}
