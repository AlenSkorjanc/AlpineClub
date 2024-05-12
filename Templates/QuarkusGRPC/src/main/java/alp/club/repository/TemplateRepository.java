package alp.club.repository;

import alp.club.models.TemplateEntity;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TemplateRepository implements PanacheMongoRepository<TemplateEntity> {
}
