package vn.edu.qnu.simplechat.server.data.entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public record Room(String roomId, Set<String> members) {
    public Room(String roomId) {
        this(roomId, Collections.emptySet());
    }

    public Room addMember(String username) {
        Set<String> newMembers = new HashSet<>(this.members);
        newMembers.add(username);
        return new Room(this.roomId, Collections.unmodifiableSet(newMembers));
    }

    public Room removeMember(String username) {
        Set<String> newMembers = new HashSet<>(this.members);
        newMembers.remove(username);
        return new Room(this.roomId, Collections.unmodifiableSet(newMembers));
    }
}
