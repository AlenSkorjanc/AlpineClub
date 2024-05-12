package alp.club.dao;

import alp.club.vao.TemplateEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class EntityRepository implements ReactivePanacheMongoRepository<TemplateEntity> {
}
