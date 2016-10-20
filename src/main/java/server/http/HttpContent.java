package server.http;

import utils.writer.Writable;

public interface HttpContent extends Writable {
    String getMediaType();
}
