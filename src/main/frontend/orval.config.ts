import { defineConfig } from 'orval';

export default defineConfig({
    todoApp: {
        input: 'http://localhost:8080/v3/api-docs', // Deine Spring Boot OpenAPI URL
        output: {
            mode: 'tags-split', // Generiert pro Controller eine eigene Datei
            target: 'src/api/endpoints', // Ausgabepfad
            schemas: 'src/api/model', // Separate Datei für Interfaces (Todo, etc.)
            client: 'react-query', // Generiert Hooks statt nur Promises
            override: {
                mutator: {
                    path: './src/api/axios-client.ts', // Pfad zu deiner Custom Instanz
                    name: 'customInstance',
                },
            },
        },
    },
});