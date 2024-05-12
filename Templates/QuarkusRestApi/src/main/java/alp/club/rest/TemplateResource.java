package alp.club.rest;

import alp.club.config.BugsnagService;
import alp.club.dao.EntityRepository;
import alp.club.vao.TemplateEntity;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/template")
public class TemplateResource {

    private static final Logger logger = LoggerFactory.getLogger(TemplateResource.class);

    @Inject
    private BugsnagService bugsnag;

    @Inject
    private EntityRepository entityRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<TemplateEntity>> getAllEntities() {
        logger.info("Getting all");
        return entityRepository.listAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<TemplateEntity> getEntityById(@PathParam("id") String id) {
        logger.info("Getting by id: {}", id);
        return entityRepository.findById(new ObjectId(id));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<TemplateEntity> addEntity(TemplateEntity entity) {
        logger.info("Adding");
        return entityRepository.persist(entity);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<TemplateEntity> updateEntity(TemplateEntity entity) {
        logger.info("Updating with id: {}", entity.getId());
        return entityRepository.update(entity);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Boolean> deleteEntity(@PathParam("id") String id) {
        logger.info("Deleting with id: {}", id);
        return entityRepository.deleteById(new ObjectId(id));
    }

    @GET
    @Path("/test-bugsnag")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> testBugsnag() {
        if(bugsnag.notify(new RuntimeException("Test error for template microservice"))) {
            return Uni.createFrom().item(Response.ok("Bugsnag working!").type(MediaType.TEXT_PLAIN).build());
        } else {
            return Uni.createFrom().item(Response.serverError().entity("Bugsnag not working!").type(MediaType.TEXT_PLAIN).build());
        }
    }
}
