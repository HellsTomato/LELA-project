import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// Vite config for local development and React support.
export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  }
})
