package dev.aksifar.workshop.tools.action;

public record Task(Long id, String title, String description, String assignee, TaskStatus status) {}
