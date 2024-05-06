package alp.club.vao;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ArticleEntity extends ReactivePanacheMongoEntity {
    private ObjectId id;
    private ObjectId authorId;
    private String title;
    private String summary;
    private String body;
    private Integer views;
    private LocalDateTime created;
}
