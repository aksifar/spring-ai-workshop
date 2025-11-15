package dev.aksifar.workshop.tools.datetime;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DateTimeController {

    private final ChatClient  chatClient;
    private final DateTimeTools dateTimeTools;

    public DateTimeController(@Qualifier("openAIChatClient")ChatClient chatClient, DateTimeTools dateTimeTools) {
        this.chatClient = chatClient;
        this.dateTimeTools = dateTimeTools;
    }

    @GetMapping("/tools")
    public String tools(){
        return chatClient.prompt()
                .user("Whats tomorrow's date?")
                .tools(dateTimeTools) //if you remove this the LLM won't be able to answer the prompt with tomorrow's date
                .call()
                .content();
    }
}
