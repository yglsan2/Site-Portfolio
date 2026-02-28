#!/usr/bin/env bash
# Deploiement du backend sur Render en ligne de commande.
# Prerequis : depot pousse sur GitHub, Blueprint cree une fois (Dashboard Render -> New -> Blueprint).

set -e
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"

echo "=== Render : backend (Docker) ==="

if ! command -v render &>/dev/null; then
  echo "Installation du Render CLI..."
  if command -v brew &>/dev/null; then
    brew install render
  else
    curl -fsSL https://raw.githubusercontent.com/render-oss/cli/refs/heads/main/bin/install.sh | sh
  fi
fi

echo "Connexion a Render..."
render login

if [ -f "render.yaml" ]; then
  echo "Validation de render.yaml..."
  render blueprints validate render.yaml 2>/dev/null || true
fi

echo ""
echo "Lancement du deploiement. Choisissez le service portfolio-api si demande."
render deploys create --wait 2>/dev/null || render deploys create

echo ""
echo "Notez l'URL du service (ex. https://portfolio-api.onrender.com) pour Netlify."
