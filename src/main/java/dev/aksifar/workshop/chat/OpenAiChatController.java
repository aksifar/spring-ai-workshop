package dev.aksifar.workshop.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class OpenAiChatController {

    private final ChatClient chatClient;

    public OpenAiChatController(@Qualifier("openAIChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/chat")
    public String chat(){
        return chatClient.prompt()
                .user("Tell me an interesting fact about Java.")
                .call()
                .content();
    }

    @GetMapping("/stream")
    public Flux<String> stream(){
        return chatClient.prompt()
                .user("tell me in under 10 words the best tourist attraction in London")
                .stream()
                .content();
    }

    @GetMapping("/joke")
    public ChatResponse joke(){
        return chatClient.prompt()
                .user("Tell me a programming joke.")
                .call()
                .chatResponse();
    }
}
