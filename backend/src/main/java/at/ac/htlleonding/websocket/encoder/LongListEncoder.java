package at.ac.htlleonding.websocket.encoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

import java.util.List;

public class LongListEncoder implements Encoder.Text<List<Long>> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String encode(List<Long> object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init(EndpointConfig config) {}

    @Override
    public void destroy() {}
}