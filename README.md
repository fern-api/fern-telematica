# Telematica Clients

This repository contains

- [Fern API Definition](./api/src/vehicleData.yml) of the Telematica APIs
- [Sample app](./sample-java-app/src/main/java/com/sample/MyApp.java) consuming [generated Java SDK](./sample-java-app/build.gradle#L16)
- [Sample app](./sample-js-app/src/app.ts) consuming [generated Typescript SDK](./sample-js-app/package.json#L10)

## Telematica Fern API Definition

The [API Definition](./api/src/vehicleData.yml) was generated by looking at existing Telematica docs and [openapi.json](./openapi.json).

After creating the API definition, we added [two generators](./api/.fernrc.yml#L3) to our [`.fernrc.yml`](./api/.fernrc.yml) - one for [java](./api/.fernrc.yml#L4) and one for [typescript](./api/.fernrc.yml#L10).

By running `fern generate` the SDKs were generated and published to a registry. Checkout how the sample apps take a [java dependency](./sample-java-app/build.gradle#L16) and a [typescript dependency](./sample-js-app/package.json#L10).

## Java Sample App

The core logic lives in [MyApp.java](./sample-java-app/src/main/java/com/sample/MyApp.java).

> Fern provides the user with an easy way to instantiate a client

```java
VehicleDataTestService client =
  VehicleDataTestService
    .getClient("https://telematica-v2.herokuapp.com/v1");
```

> and type safety when invoking different endpoints.

```java
SocResponse res = client.getSOC(
  AuthHeader.valueof("ey..."),
  VehicleId.valueof("id..."));
```

## Typescript Sample App

The core logic lives in [app.ts](./sample-js-app/src/app.ts).

> Fern provides the user with an easy way to instantiate a client

```typescript
const client = new VehicleDataService({
  origin: "https://telematica-v2.herokuapp.com/v1",
  token: "token...",
});
```

> and type safety when invoking different endpoints.

```typescript
const socResponse = await client.getSOC({
  vehicleId: VehicleId.valueOf("id..."),
});
```

_Run the sample app by doing:_

```bash
cd sample-js-app
yarn install
yarn run:app
```

## Notes about existing API

Some things we noticed about the existing API:

- Telematica uses `vehicleId` as a required query parameter. Query parameters are generally optional and used to filter REST API results whereas path parameters are required and used to point to a specific resource within a collection. In the future, we may want to migrate to a path parameter for `vehicleId`.
- The `test` endpoints are redefined with paths that are suffixed with `/test`. Instead of suffixing, if we prefixed then we could define one [service](./api/src/vehicleData.yml#L36) and have the Telematica server expose 2 implementations of the service at different paths (one at `/vehicle-data` and the other at `/test/vehicle-data`). This way you wouldn't need to keep mantaining a copy of each endpoint in the [`VehicleDataTestService`](./api/src/vehicleData.yml#L76).
- The documentation at https://telematica.readme.io/reference/getlocationtest doesn't document the schema of the response. You have to hit the API to see that.
- The [openapi.json](./openapi.json) doesn't document that [ILocation](./openapi.json#L686) has 2 properties, `lat` and `lng`.
