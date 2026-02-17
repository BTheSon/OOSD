package vn.edu.qnu.simplechat.shared.protocol.response;

import vn.edu.qnu.simplechat.shared.protocol.Packet;

public record ErrorMessage(
        String msg
) implements Packet { }
