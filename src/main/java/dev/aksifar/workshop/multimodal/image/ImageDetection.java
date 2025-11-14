package dev.aksifar.workshop.multimodal.image;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageDetection {

    private final ChatClient chatClient;

    @Value(("classpath:images/20251006_134318.jpg"))
    private Resource sampleImage;

    public ImageDetection(@Qualifier("openAIChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/image-to-text")
    public String imageToText() {
        return chatClient.prompt()
                .user(u -> {
                    u.text("Please describe the content of this image?");
                    u.media(MimeTypeUtils.IMAGE_JPEG, sampleImage);
                })
                .call()
                .content();
    }
}