package at.ac.htlleonding.websocket.encoder;

import at.ac.htlleonding.entities.Turn;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class TurnEncoder implements Encoder.Text<Turn> {

    private static final ObjectMapper mapper = new ObjectMapper();

    public String encode(Turn turn) {
        System.out.println("ENCODING TURN");
        try {
            return mapper.writeValueAsString(turn);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init(EndpointConfig config) {}

    @Override
    public void destroy() {}
}