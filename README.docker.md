# Configuration Docker pour StockNCook

## Prérequis

- Docker et Docker Compose installés sur votre machine
- Java 21 (pour le développement local)

## Démarrage rapide

### Construire et démarrer l'application avec Docker Compose

```bash
docker-compose up -d --build
```

Cette commande va:
1. Construire l'image Docker de l'application
2. Démarrer un conteneur PostgreSQL
3. Démarrer l'application en se connectant à la base de données

### Arrêter l'application

```bash
docker-compose down
```

### Consulter les logs

```bash
docker-compose logs -f app
```

## Configuration

Vous pouvez modifier les variables d'environnement dans le fichier `.env` pour personnaliser votre déploiement.

## Volumes persistants

Les données PostgreSQL sont persistées dans un volume Docker nommé `postgres-data`.

## Accès

- Application web: http://localhost:8080
- Documentation API (Swagger): http://localhost:8080/swagger-ui/index.html
- Base de données PostgreSQL: localhost:5432

## Personnalisation

Vous pouvez modifier les fichiers suivants pour adapter la configuration à vos besoins:

- `Dockerfile`: Personnaliser le processus de build
- `docker-compose.yml`: Modifier les services, ports, volumes, etc.
- `.env`: Changer les variables d'environnement
