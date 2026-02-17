package vn.edu.qnu.simplechat.server.data.entity;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public record Room(
        String roomId,
        Set<String> members,
        Queue<Message> messageList
) {

    public Room(String roomId) {
        this(
                roomId,
                ConcurrentHashMap.newKeySet(),      // thread-safe set
                new ConcurrentLinkedQueue<>()       // append cực nhanh
        );
    }

    // ====== MUTABLE OPERATIONS ======

    public boolean addMember(String username) {
        return members.add(username);
    }

    public boolean removeMember(String username) {
        return members.remove(username);
    }

    public boolean addMessage(Message msg) {
        return messageList.add(msg);
    }
}
