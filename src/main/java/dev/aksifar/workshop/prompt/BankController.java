package dev.aksifar.workshop.prompt;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank")
public class BankController {

    private final ChatClient chatClient;

    public BankController(@Qualifier("openAIChatClient")ChatClient chatClient) {
        this.chatClient = chatClient;
    }


    @GetMapping ("/chat/{message}")
    public String chat(@PathVariable String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();

    }
}
