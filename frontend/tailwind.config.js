/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        portfolio: {
          bg: '#0f0f12',
          card: '#1a1a20',
          text: '#e8e6e3',
          muted: '#8b8b8b',
          accent: '#6c9ef8',
          'accent-hover': '#8bb3ff',
        }
      },
      fontFamily: {
        sans: ['Inter', 'Segoe UI', 'system-ui', 'sans-serif'],
      },
    },
  },
  plugins: [],
}
