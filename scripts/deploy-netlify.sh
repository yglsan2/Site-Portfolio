#!/usr/bin/env bash
# Deploiement du frontend sur Netlify en ligne de commande.

set -e
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"

echo "=== Netlify : frontend Vue ==="

if [ -z "$VITE_API_URL" ]; then
  echo "Entrez l'URL de l'API (backend Render), ex. https://portfolio-api.onrender.com"
  read -r RENDER_URL
  RENDER_URL="${RENDER_URL%/}"
  export VITE_API_URL="${RENDER_URL}/api"
fi

echo "Connexion a Netlify..."
npx --yes netlify-cli login

if [ ! -f "frontend/.netlify/state.json" ] && [ ! -f ".netlify/state.json" ]; then
  echo "Configuration du site Netlify (init)..."
  npx --yes netlify-cli init
fi

echo "Definition de VITE_API_URL..."
npx --yes netlify-cli env:set VITE_API_URL "$VITE_API_URL" --context production

echo "Build et deploiement..."
npx --yes netlify-cli deploy --build --prod

echo "Deploiement Netlify termine."
