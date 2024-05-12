package alp.club.vao;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@MongoEntity
public class TemplateEntity extends ReactivePanacheMongoEntity {
    private ObjectId id;
}
