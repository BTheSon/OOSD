package vn.edu.qnu.simplechat.shared.protocol.response;

import vn.edu.qnu.simplechat.shared.protocol.Packet;

public record MessageFromServer (String msg) implements Packet { }
