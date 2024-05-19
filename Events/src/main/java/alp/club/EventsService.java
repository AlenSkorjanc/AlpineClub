package alp.club;

import alp.club.config.BugsnagService;
import alp.club.models.EventEntity;
import alp.club.repository.EventsRepository;
import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;
import io.quarkus.mongodb.panache.PanacheQuery;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class EventsService extends EventServiceGrpc.EventServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(EventsService.class);

    @Inject
    private BugsnagService bugsnagService;

    @Inject
    private EventsRepository eventsRepository;

    @Override
    public void getAllEvents(EmptyRequest request, StreamObserver<EventsResponse> responseObserver) {
        logger.info("Getting all events");
        PanacheQuery<EventEntity> eventEntities = eventsRepository.findAll();

        List<Event> events = eventEntities.list().stream()
                .map(this::mapEventEntityToEvent)
                .collect(Collectors.toList());

        EventsResponse response = EventsResponse.newBuilder()
                .addAllEvents(events)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getEventById(EventIdRequest request, StreamObserver<EventResponse> responseObserver) {
        logger.info("Getting event by id: " + request.getId());
        ObjectId eventId = new ObjectId(request.getId());
        EventEntity eventEntity = eventsRepository.findById(eventId);
        if (eventEntity != null) {
            Event event = mapEventEntityToEvent(eventEntity);
            EventResponse response = EventResponse.newBuilder().setEvent(event).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new RuntimeException("Event not found"));
        }
    }

    @Override
    public void addEvent(EventRequest request, StreamObserver<EventResponse> responseObserver) {
        logger.info("Adding event");
        Event event = request.getEvent();
        EventEntity eventEntity = mapEventToEventEntity(event);
        eventEntity.setId(new ObjectId());
        eventsRepository.persist(eventEntity);
        EventResponse response = EventResponse.newBuilder().setEvent(event).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateEvent(EventRequest request, StreamObserver<EventResponse> responseObserver) {
        Event event = request.getEvent();
        logger.info("Updating event by id: " + event.getId());
        EventEntity existingEventEntity = eventsRepository.findById(new ObjectId(event.getId()));
        if (existingEventEntity != null) {
            EventEntity updatedEventEntity = mapEventToEventEntity(event);
            eventsRepository.update(updatedEventEntity);
            EventResponse response = EventResponse.newBuilder().setEvent(event).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new RuntimeException("Event not found"));
        }
    }

    @Override
    public void deleteEventById(EventIdRequest request, StreamObserver<EmptyResponse> responseObserver) {
        logger.info("Deleting event by id: " + request.getId());
        ObjectId eventId = new ObjectId(request.getId());
        EventEntity existingEventEntity = eventsRepository.findById(eventId);
        if (existingEventEntity != null) {
            eventsRepository.delete(existingEventEntity);
            responseObserver.onNext(EmptyResponse.newBuilder().build());
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new RuntimeException("Event not found"));
        }
    }

    private Event mapEventEntityToEvent(EventEntity eventEntity) {
        return Event.newBuilder()
                .setId(eventEntity.getId().toString())
                .setUserId(eventEntity.getUserId())
                .setName(eventEntity.getName())
                .setDescription(eventEntity.getDescription())
                .setStart(eventEntity.getStart() == null ? 0 : eventEntity.getStart())
                .setEnd(eventEntity.getEnd() == null ? 0 : eventEntity.getEnd())
                .build();
    }

    private EventEntity mapEventToEventEntity(Event event) {
        EventEntity eventEntity = new EventEntity();
        if(!event.getId().isEmpty()) {
            eventEntity.setId(new ObjectId(event.getId()));
        }
        eventEntity.setUserId(event.getUserId());
        eventEntity.setName(event.getName());
        eventEntity.setDescription(event.getDescription());
        eventEntity.setStart(event.getStart());
        eventEntity.setEnd(eventEntity.getEnd());

        return eventEntity;
    }

    @Override
    public void testBugsnag(EmptyRequest request, StreamObserver<MessageResponse> responseObserver) {
        if(bugsnagService.notify(new RuntimeException("Test error for Events microservice"))) {
            responseObserver.onNext(MessageResponse.newBuilder().setMessage("Bugsnag working!").build());
            responseObserver.onCompleted();
        } else {
            responseObserver.onNext(MessageResponse.newBuilder().setMessage("Bugsnag not working!").build());
            responseObserver.onCompleted();
        }
    }
}
