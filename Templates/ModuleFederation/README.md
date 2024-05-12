# Template for Module Federation micro frontend
### for Alpine Club

## Getting Started

1. **Install Node modules**:
   - Run this command to install Node modules:
```shell script
     npm install
```

2. **Configure Environment variables**:
   - Open the [`.env`](.env) file.
   - Set the connection URL for a service.

3. **Rename Packages and Classes**:
   - Navigate to the [`src`](src) directory.
   - Rename the [`App.jsx`](src/App.jsx) file and all [`Example.jsx`](src/Example.jsx) to suit your project's naming conventions.

4. **Start Coding**:
   - Begin developing your Module Federation micro frontend by changing and adding components as needed.

5. **Start the app**:
   - Run this command to start the app:
```shell script
   npm start
```

## Project Structure

- [**`.env`**](.env): Configuration file for application-specific properties like service connection details and API keys.
- [**`src`**](src): Root directory containing the components for your application.
- [**`Example.jsx`**](src/Example.jsx): Example implementation of a component with a GET call to a service.
- [**`webpack.config.js`**](webpack.config.js): configuration for webpack, here you can expose your components of consume components of other micro frontends. 