package dev.aksifar.workshop.tools.action;

public record TaskResult( Long taskId,
                          String title,
                          String status,
                          String assignee,
                          String message) {}
