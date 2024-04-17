package alp.club;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import com.google.protobuf.Timestamp;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.grpc.GrpcService;
import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@QuarkusTest
class HelloGrpcServiceTest {

    @GrpcClient
    EventService eventService;

    @Test
    void testGrpcService() {
        Event event = Event.newBuilder()
                .setName("name")
                .setDescription("desc")
                .setStart(Timestamp.newBuilder().setSeconds(1615765200).build())
                .setEnd(Timestamp.newBuilder().setSeconds(1615768800).build())
                .build();

        EventResponse response = eventService.addEvent(EventRequest.newBuilder().setEvent(event).build()).await().atMost(Duration.ofSeconds(10));
        assertEquals("name", response.getEvent().getName());
    }
}
