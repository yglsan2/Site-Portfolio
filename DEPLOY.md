# Déploiement (Render + Netlify)

## En ligne de commande (recommandé)

**Première fois uniquement** : créez le Blueprint Render une fois en ouvrant  
[https://dashboard.render.com/select-repo?type=blueprint](https://dashboard.render.com/select-repo?type=blueprint)  
et en connectant le dépôt **yglsan2/Site-Portfolio**. Ensuite tout se fait en CLI.

```bash
# Tout en un (backend puis frontend)
./scripts/deploy-all.sh
```

Ou en deux temps :

```bash
# 1. Backend Render
./scripts/deploy-render.sh

# 2. Frontend Netlify (indiquer l’URL Render quand demandé)
./scripts/deploy-netlify.sh
```

Les scripts installent les CLI (Render, Netlify via npx) si besoin, ouvrent le navigateur pour la connexion, puis lancent le déploiement.

---

## À la main (Dashboard)

### Backend sur Render

1. Créer un compte sur [Render](https://render.com).
2. **New** → **Web Service**.
3. Connecter le dépôt GitHub **yglsan2/Site-Portfolio**.
4. Configurer :
   - **Root Directory** : `backend`
   - **Environment** : **Docker**
   - **Instance type** : Free
5. Créer le service. Render build le `Dockerfile` dans `backend/` et expose une URL du type `https://portfolio-api.onrender.com`.
6. Noter cette URL : tu en auras besoin pour Netlify.

---

### Frontend sur Netlify

1. Créer un compte sur [Netlify](https://netlify.com).
2. **Add new site** → **Import an existing project** → GitHub → **yglsan2/Site-Portfolio**.
3. Netlify détecte le `netlify.toml` à la racine (base = `frontend`, build = `npm run build`, publish = `dist`). Ne rien changer si tout est cohérent.
4. **Variables d’environnement** (à ajouter dans **Site settings** → **Environment variables**) :
   - **Key** : `VITE_API_URL`
   - **Value** : `https://TON-BACKEND.onrender.com/api`  
     (remplacer `TON-BACKEND` par le nom réel de ton service Render, ex. `https://portfolio-api.onrender.com/api`)
5. Déployer. Le site sera en ligne sur une URL du type `https://xxx.netlify.app`.

---

## Ordre conseillé

1. Déployer d’abord le **backend sur Render** et attendre que le service soit vert.
2. Copier l’URL du backend (ex. `https://portfolio-api.onrender.com`).
3. Déployer le **frontend sur Netlify** en mettant `VITE_API_URL` = `https://portfolio-api.onrender.com/api`.
4. Tester le site Netlify : il doit appeler l’API Render sans erreur CORS.
