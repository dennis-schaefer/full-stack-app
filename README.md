# full-stack-app

## Project setup
- Create a Spring Boot 4.0 Application with [start.spring.io](https://start.spring.io). The application requires the
  `spring-boot-starter-webmvc` dependency.
- Inside the `src/main` directory, create a new Vite project for React and install `react-router-dom`:
  ```bash
  cd src/main
  npm create vite@latest frontend -- --template react-ts
  cd frontend
  npm install react-router-dom
  ```


## Notes
- JEP 519 (Compact Object Headers) currently as opt-in with `java -XX:+UseCompactObjectHeaders -jar app.jar`

## ToDo's
- Websocket testen