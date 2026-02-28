#!/usr/bin/env bash
# Déploiement complet : Render (backend) puis Netlify (frontend).
# La première fois, créez le Blueprint Render : https://dashboard.render.com/select-repo?type=blueprint

set -e
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"

echo "=============================================="
echo "  Déploiement portfolio (Render + Netlify)"
echo "=============================================="
echo ""

# 1. Backend Render
"$ROOT/scripts/deploy-render.sh"

echo ""
echo "---"
echo "Quand le backend est en ligne, copiez son URL (ex. https://portfolio-api.onrender.com)."
echo "Entrez l’URL du backend Render (sans /api à la fin) :"
read -r RENDER_URL
RENDER_URL="${RENDER_URL%/}"

# 2. Frontend Netlify avec cette URL
export VITE_API_URL="${RENDER_URL}/api"
"$ROOT/scripts/deploy-netlify.sh"

echo ""
echo "=============================================="
echo "  Déploiement terminé"
echo "=============================================="
echo "Backend : $RENDER_URL"
echo "Frontend : voir l’URL affichée par Netlify ci-dessus."
