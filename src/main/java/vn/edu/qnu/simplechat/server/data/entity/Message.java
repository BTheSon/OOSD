package vn.edu.qnu.simplechat.server.data.entity;

import java.io.Serializable;
import java.util.UUID;

public record Message(
        UUID id,
        String roomId,
        String username,
        String message
) implements Serializable {
    public Message(String roomId, String username, String message) {
        this(UUID.randomUUID(), roomId, username, message);
    }
}
