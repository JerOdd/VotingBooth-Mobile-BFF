# mobile-bff-service
Mobile Backend for Frontend Service for VotingBooth

It exposes the endpoints the mobile app needs and calls law-api and representative-api behind them.

| Endpoint                         | Calls                                   |
|----------------------------------|-----------------------------------------|
| `GET /laws?page&size&status&category` | law-api `GET /laws`                |
| `GET /representatives`           | representative-api `GET /representatives` |
| `GET /representatives/{id}`      | representative-api `GET /representatives/{id}` |

Errors are `ProblemDetail`s: 400 for an invalid page, 404 for an unknown representative, 502 when an API fails or
can't be reached.

## Running locally

`docker-compose.yml` builds and starts law-api (host port 8083) and representative-api (host port 8085) from the
sibling `../law-service` and `../representative-service` checkouts, each with its own Postgres.

```sh
docker compose up -d --build
```

Their databases start empty. To use the data the services' jobs already synced, start each service's own compose and
point the APIs at it:

```sh
LAW_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5434/laws \
REPRESENTATIVE_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5433/representatives \
docker compose up -d --build
```

The `local` profile points the BFF at them. It needs JDK 21:

```sh
./mvnw -B package -DskipTests
java -jar target/mobile-bff-service-0.0.1-SNAPSHOT.jar --spring.profiles.active=local   # BFF on :8086
```

Without JDK 21, run the image instead:

```sh
docker build -t mobile-bff-service .
docker run --rm -p 8086:8086 \
  -e LAW_API_URL=http://host.docker.internal:8083 \
  -e REPRESENTATIVE_API_URL=http://host.docker.internal:8085 mobile-bff-service
```

Outside the `local` profile, `LAW_API_URL` and `REPRESENTATIVE_API_URL` must be set.

`docker compose down -v` removes the containers and their data.
