package alp.club;

import io.quarkus.grpc.GrpcClient;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class HelloGrpcServiceTest {

    @GrpcClient
    EventService eventService;

    @Test
    void testGrpcService() {
        Event event = Event.newBuilder()
                .setName("name")
                .setDescription("desc")
                .setStart(1615765200)
                .setEnd(1615768800)
                .build();

        EventResponse response = eventService.addEvent(EventRequest.newBuilder().setEvent(event).build()).await().atMost(Duration.ofSeconds(10));
        assertEquals("name", response.getEvent().getName());
    }
}
