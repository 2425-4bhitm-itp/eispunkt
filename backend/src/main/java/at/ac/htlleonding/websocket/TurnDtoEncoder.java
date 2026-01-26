package at.ac.htlleonding.websocket;

import at.ac.htlleonding.dto.TurnDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class TurnDtoEncoder implements Encoder.Text<TurnDto> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String encode(TurnDto dto) {
        try {
            System.out.println("ENCODING TurnDto");
            return mapper.writeValueAsString(dto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override public void init(EndpointConfig config) {}
    @Override public void destroy() {}
}
