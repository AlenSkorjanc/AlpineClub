# Template for Quarkus REST API
### for Alpine Club

## Getting Started

1. **Configure Maven**:
    - Open the [`pom.xml`](pom.xml) file and set the ArtifactID.

2. **Configure Application Properties**:
    - Open the [`application.properties`](src/main/resources/application.properties) file located in [`src/main/resources/`](src/main/resources).
    - Set the connection URI for MongoDB, the MongoDB database name, and the API key for Bugsnag.

3. **Rename Packages and Classes**:
    - Navigate to the [`src/main/java/alp/club`](src/main/java/alp/club) directory.
    - Rename the classes within it to suit your project's naming conventions.

4. **Start Coding**:
    - Begin developing your REST API by changing and adding controllers, services, and other components as needed.

## Project Structure

- [**`pom.xml`**](pom.xml): Contains project metadata and dependencies managed by Maven.
- [**`src/main/resources/application.properties`**](src/main/resources/application.properties): Configuration file for application-specific properties like database connection details and API keys.
- [**`src/main/java/alp/club`**](src/main/java/alp/club): Root package containing the Java classes for your application.
- [**`TemplateResource`**](src/main/java/alp/club/rest/TemplateResource.java): Example implementation of REST API and CRUD operations for [`TemplateEntity`](src/main/java/alp/club/vao/TemplateEntity.java) and endpoint for testing Bugsnag service.
