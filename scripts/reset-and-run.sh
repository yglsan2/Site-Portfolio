#!/usr/bin/env bash
# Réinitialise la base H2 du backend (pour que DataInitializer recrée projets + snippets)
# puis démarre le backend et le frontend.

set -e
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
BACKEND="$ROOT/backend"
FRONTEND="$ROOT/frontend"
DATA_DIR="$BACKEND/data"

echo "=== 1. Arrêt des processus sur les ports 8080 et 5173 (si besoin) ==="
for port in 8080 5173; do
  if command -v lsof &>/dev/null; then
    pid=$(lsof -ti ":$port" 2>/dev/null) && { kill "$pid" 2>/dev/null; echo "Arrêt du processus sur le port $port (PID $pid)"; } || true
  fi
done

echo ""
echo "=== 2. Réinitialisation de la base de données (suppression de backend/data/) ==="
if [ -d "$DATA_DIR" ]; then
  rm -rf "$DATA_DIR"
  echo "Répertoire $DATA_DIR supprimé. Au prochain démarrage, DataInitializer recréera les projets et snippets."
else
  echo "Aucun répertoire data trouvé (déjà vide ou premier run)."
fi

echo ""
echo "=== 3. Démarrage du backend (Spring Boot sur le port 8080) ==="
cd "$BACKEND"
mvn -q spring-boot:run &
BACKEND_PID=$!
echo "Backend démarré (PID $BACKEND_PID). Attente du démarrage (environ 20–30 s)..."
sleep 25
if kill -0 $BACKEND_PID 2>/dev/null; then
  echo "Backend prêt."
else
  echo "Le backend a peut-être planté. Vérifiez les logs ci-dessus."
fi

echo ""
echo "=== 4. Démarrage du frontend (Vite sur le port 5173) ==="
cd "$FRONTEND"
npm run dev &
FRONTEND_PID=$!
echo "Frontend démarré (PID $FRONTEND_PID)."
echo ""
echo "---"
echo "Backend : http://localhost:8080"
echo "Frontend : http://localhost:5173"
echo "Pour arrêter : kill $BACKEND_PID $FRONTEND_PID"
echo "---"

wait
