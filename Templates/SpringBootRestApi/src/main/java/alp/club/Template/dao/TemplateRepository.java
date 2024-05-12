package alp.club.Template.dao;

import alp.club.Template.vao.TemplateEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TemplateRepository extends MongoRepository<TemplateEntity, String> {
}
