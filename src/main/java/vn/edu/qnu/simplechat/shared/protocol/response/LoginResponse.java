package vn.edu.qnu.simplechat.shared.protocol.response;

import vn.edu.qnu.simplechat.shared.protocol.Packet;

public record LoginResponse(
        boolean isSuccess,
        String message
) implements Packet {
}
