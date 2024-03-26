package alp.club.dao;

import alp.club.vao.ArticleEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

@ApplicationScoped
public class ArticlesRepository implements ReactivePanacheMongoRepository<ArticleEntity> {

    public Uni<ArticleEntity> addArticle(ArticleEntity article) {
        article.setId(new ObjectId());
        article.setCreated(new Date());
        return persist(article);
    }

    public Uni<List<ArticleEntity>> listAllArticles() {
        return findAll().list();
    }

}
