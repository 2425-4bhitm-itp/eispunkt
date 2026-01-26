package at.ac.htlleonding.websocket.encoder;

import at.ac.htlleonding.dto.Score;
import at.ac.htlleonding.dto.TurnDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class ScoreEncoder implements Encoder.Text<Score> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String encode(Score dto) {
        try {
            System.out.println("ENCODING Score");
            return mapper.writeValueAsString(dto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override public void init(EndpointConfig config) {}
    @Override public void destroy() {}
}
