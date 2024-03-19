package alp.club.repository;

import alp.club.models.EventEntity;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EventsRepository implements PanacheMongoRepository<EventEntity> {
}
