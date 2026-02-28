#!/usr/bin/env bash
# Supprime la base H2 du backend. Au prochain démarrage du backend, DataInitializer
# recréera le profil, les projets et les snippets.

set -e
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
DATA_DIR="$ROOT/backend/data"

if [ -d "$DATA_DIR" ]; then
  rm -rf "$DATA_DIR"
  echo "Base réinitialisée : $DATA_DIR supprimé."
else
  echo "Aucune base à réinitialiser ($DATA_DIR absent)."
fi
