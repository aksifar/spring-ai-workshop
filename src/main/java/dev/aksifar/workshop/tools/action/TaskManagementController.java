package dev.aksifar.workshop.tools.action;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskManagementController {

    private final ChatClient chatClient;
    private final TaskManagementTools taskManagementTools;

    public TaskManagementController(@Qualifier("openAIChatClient") ChatClient chatClient, TaskManagementTools taskManagementTools) {
        this.chatClient = chatClient;
        this.taskManagementTools = taskManagementTools;
    }

    @GetMapping("/tasks")
    public Task tools(@RequestParam String message) {
        return chatClient.prompt()
                .user(message)
                .tools(taskManagementTools)
                .call()
                .entity(Task.class);
    }
}