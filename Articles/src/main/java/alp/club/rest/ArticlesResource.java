package alp.club.rest;

import alp.club.config.BugsnagService;
import alp.club.dao.ArticlesRepository;
import alp.club.jms.consumer.ViewsConsumer;
import alp.club.vao.ArticleEntity;
import com.bugsnag.Bugsnag;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/articles")
public class ArticlesResource {

    private static final Logger logger = LoggerFactory.getLogger(ArticlesResource.class);

    @Inject
    private BugsnagService bugsnag;

    @Inject
    private ViewsConsumer consumer;

    @Inject
    private ArticlesRepository articlesRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<ArticleEntity>> getAllArticles() {
        logger.info("Getting articles");
        return articlesRepository.listAllArticles();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<ArticleEntity> getArticleById(@PathParam("id") String id) {
        logger.info("Getting article by id: {}", id);
        return articlesRepository.findById(new ObjectId(id));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<ArticleEntity> addArticle(ArticleEntity article) {
        logger.info("Adding article");
        return articlesRepository.addArticle(article);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<ArticleEntity> updateArticle(ArticleEntity article) {
        logger.info("Updating article with id: {}", article.getId());
        if(article.getViews() == null || article.getViews() == 0) {
            article.setViews(consumer.getLastViews());
        }
        return articlesRepository.update(article);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Boolean> deleteArticle(@PathParam("id") String id) {
        logger.info("Deleting article with id: {}", id);
        return articlesRepository.deleteById(new ObjectId(id));
    }

    @GET
    @Path("/last-views")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Integer> getArticleById() {
        logger.info("Getting last views");
        consumer = null;
        return Uni.createFrom().item(consumer.getLastViews());
    }

    @GET
    @Path("/test-bugsnag")
    public Uni<Response> testBugsnag() {
        if(bugsnag.notify(new RuntimeException("Test error for Articles microservice"))) {
            return Uni.createFrom().item(Response.ok("Bugsnag working!").type(MediaType.TEXT_PLAIN).build());
        } else {
            return Uni.createFrom().item(Response.serverError().entity("Bugsnag working!").type(MediaType.TEXT_PLAIN).build());
        }
    }
}
