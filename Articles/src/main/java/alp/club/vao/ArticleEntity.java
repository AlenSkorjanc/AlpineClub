package alp.club.vao;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@MongoEntity
public class ArticleEntity extends ReactivePanacheMongoEntity {
    private ObjectId id;
    private ObjectId authorId;
    private String title;
    private String body;
    private Integer views;
    private Date created;
}
