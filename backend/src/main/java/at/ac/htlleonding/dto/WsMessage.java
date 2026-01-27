package at.ac.htlleonding.dto;

public record WsMessage<T>(String type, T payload) {}

