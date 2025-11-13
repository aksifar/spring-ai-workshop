package dev.aksifar.workshop.prompt;

import dev.aksifar.workshop.model.Itinerary;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vacation")
public class VaccationController {

    private final ChatClient chatClient;

    public VaccationController(@Qualifier("openAIChatClient")ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/structured")
    public Itinerary getStructuredItinerary() {

        return chatClient.prompt()
                .user("I am going to London for 3 days, please provide me with a compact itinerary.")
                .call()
                .entity(Itinerary.class);


    }


}
