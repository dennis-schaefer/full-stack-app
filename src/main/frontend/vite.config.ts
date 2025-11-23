import { defineConfig } from 'vite'
import path from "path"
import tailwindcss from "@tailwindcss/vite"
import react from '@vitejs/plugin-react'

export default defineConfig({
    plugins: [react(), tailwindcss()],
    resolve: {
        alias: {
            "@": path.resolve(__dirname, "./src"),
        },
    },
    server: {
        port: 3000,
        proxy: {
            '/api': {
                target: 'http://localhost:8080',
                changeOrigin: true,
                secure: false
            },
            '/ws': {
                target: 'ws://localhost:8080',
                ws: true,
                secure: false
            },
            '/oauth2': {
                target: 'http://localhost:8080',
                changeOrigin: true,
                secure: false
            },
            '/logout': {
                target: 'http://localhost:8080',
                changeOrigin: true,
                secure: false
            }
        }
    },
    build: {
        outDir: '../resources/static/',
        emptyOutDir: true,
    },
})
