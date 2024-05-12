package alp.club;

import alp.club.config.BugsnagService;
import alp.club.models.TemplateEntity;
import alp.club.repository.TemplateRepository;
import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;
import io.quarkus.mongodb.panache.PanacheQuery;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class TemplateServiceImplementation extends TemplateServiceGrpc.TemplateServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(TemplateServiceImplementation.class);

    @Inject
    private BugsnagService bugsnagService;

    @Inject
    private TemplateRepository templateRepository;

    @Override
    public void getAll(EmptyRequest request, StreamObserver<TemplatesResponse> responseObserver) {
        logger.info("Getting all");
        PanacheQuery<TemplateEntity> entities = templateRepository.findAll();

        List<Template> templates = entities.list().stream()
                .map(this::mapTemplateEntityToTemplate)
                .collect(Collectors.toList());

        TemplatesResponse response = TemplatesResponse.newBuilder()
                .addAllTemplates(templates)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getById(TemplateIdRequest request, StreamObserver<TemplateResponse> responseObserver) {
        logger.info("Getting by id: " + request.getId());
        ObjectId id = new ObjectId(request.getId());
        TemplateEntity entity = templateRepository.findById(id);
        if (entity != null) {
            Template template = mapTemplateEntityToTemplate(entity);
            TemplateResponse response = TemplateResponse.newBuilder().setTemplate(template).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new RuntimeException("Not found"));
        }
    }

    @Override
    public void add(TemplateRequest request, StreamObserver<TemplateResponse> responseObserver) {
        logger.info("Adding");
        Template template = request.getTemplate();
        TemplateEntity templateEntity = mapTemplateToTemplateEntity(template);
        templateEntity.setId(new ObjectId());
        templateRepository.persist(templateEntity);
        TemplateResponse response = TemplateResponse.newBuilder().setTemplate(template).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void update(TemplateRequest request, StreamObserver<TemplateResponse> responseObserver) {
        Template template = request.getTemplate();
        logger.info("Updating by id: " + template.getId());
        TemplateEntity existingEntity = templateRepository.findById(new ObjectId(template.getId()));
        if (existingEntity != null) {
            TemplateEntity updatedEntity = mapTemplateToTemplateEntity(template);
            templateRepository.update(updatedEntity);
            TemplateResponse response = TemplateResponse.newBuilder().setTemplate(template).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new RuntimeException("Not found"));
        }
    }

    @Override
    public void deleteById(TemplateIdRequest request, StreamObserver<EmptyResponse> responseObserver) {
        logger.info("Deleting by id: " + request.getId());
        ObjectId id = new ObjectId(request.getId());
        TemplateEntity existingTemplateEntity = templateRepository.findById(id);
        if (existingTemplateEntity != null) {
            templateRepository.delete(existingTemplateEntity);
            responseObserver.onNext(EmptyResponse.newBuilder().build());
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new RuntimeException("Not found"));
        }
    }

    private Template mapTemplateEntityToTemplate(TemplateEntity templateEntity) {
        return Template.newBuilder()
                .setId(templateEntity.getId().toString())
                .build();
    }

    private TemplateEntity mapTemplateToTemplateEntity(Template template) {
        TemplateEntity templateEntity = new TemplateEntity();
        if (!template.getId().isEmpty()) {
            templateEntity.setId(new ObjectId(template.getId()));
        }
        return templateEntity;
    }

    @Override
    public void testBugsnag(EmptyRequest request, StreamObserver<MessageResponse> responseObserver) {
        if (bugsnagService.notify(new RuntimeException("Test error for Template microservice"))) {
            responseObserver.onNext(MessageResponse.newBuilder().setMessage("Bugsnag working!").build());
            responseObserver.onCompleted();
        } else {
            responseObserver.onNext(MessageResponse.newBuilder().setMessage("Bugsnag not working!").build());
            responseObserver.onCompleted();
        }
    }
}
