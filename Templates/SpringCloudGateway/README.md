# Template for Spring Cloud Gateway
### for Alpine Club

## Getting Started

1. **Configure Maven**:
   - Open the [`pom.xml`](pom.xml) file and set the ArtifactID, Name, and Description of your service.

2. **Configure Application Properties**:
   - Open the [`application.properties`](src/main/resources/application.properties) file located in [`src/main/resources/`](src/main/resources).
   - Set the connection URL to a service and the API key for Bugsnag.

3. **Rename Packages and Classes**:
   - Navigate to the [`src/main/java/alp/club`](src/main/java/alp/club) directory.
   - Rename the classes within it to suit your project's naming conventions.

4. **Start Coding**:
   - Begin developing your Spring Cloud Gateway by changing and adding paths to function `routeLocator()` in [`TemplateGatewayApplication`](src/main/java/alp/club/TemplateGatewayApplication.java).

## Project Structure

- [**`pom.xml`**](pom.xml): Contains project metadata and dependencies managed by Maven.
- [**`src/main/resources/application.properties`**](src/main/resources/application.properties): Configuration file for application-specific properties like API URLs and API keys.
- [**`src/main/java/alp/club`**](src/main/java/alp/club): Root package containing the Java classes for your application.
- [**`TemplateGatewayApplication`**](src/main/java/alp/club/Template/rest/TemplateController.java): Example implementation of Spring Cloud Gateway.
