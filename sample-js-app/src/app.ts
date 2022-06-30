import { VehicleId } from "@telematica-fern/telematica-api-client/model/vehicleData";
import {
  VehicleDataService,
  VehicleDataTestService,
} from "@telematica-fern/telematica-api-client/services/vehicleData";
import * as dotenv from "dotenv";

const dotEnvOutput = dotenv.config({ path: __dirname + "/../../.env" });
if (dotEnvOutput.parsed === null || dotEnvOutput.parsed === undefined) {
  throw Error("Failed to parse .env file: " + dotEnvOutput.error?.message);
}
const env = dotEnvOutput.parsed;

demoWithTestingService();

async function demoWithTestingService() {
  const testVehicleDataService = new VehicleDataTestService({
    origin: "https://telematica-v2.herokuapp.com/v1",
    token: env["TELEMATICA_TEST_TOKEN"],
  });
  const vehicleId = VehicleId.of("61fe4a188a4c4d1ef9ba0b62");

  const socResponse = await testVehicleDataService.getSOCTest({ vehicleId });
  if (!socResponse.ok) {
    throw new Error("Failed to get soc from Telematica!");
  }
  console.log("Soc of the vehicle is ", socResponse.body.soc);

  const locationResponse = await testVehicleDataService.getLocationTest({
    vehicleId,
  });
  if (!locationResponse.ok) {
    throw new Error("Failed to get location from Telematica!");
  }
  console.log(
    "Location of the vehicle is (%d, %d)",
    locationResponse.body.location.lat,
    locationResponse.body.location.lng
  );
}

async function demoWithRealService() {
  const vehicleDataService = new VehicleDataService({
    origin: "https://telematica-v2.herokuapp.com/v1",
    token: env["TELEMATICA_TOKEN"],
  });
  const vehicleId = VehicleId.of("REAL_VEHICLE_ID");

  const socResponse = await vehicleDataService.getSOC({ vehicleId });
  if (!socResponse.ok) {
    throw new Error("Failed to get soc from Telematica!");
  }
  console.log("Soc of the vehicle is ", socResponse.body.soc);

  const locationResponse = await vehicleDataService.getLocation({
    vehicleId,
  });
  if (!locationResponse.ok) {
    throw new Error("Failed to get location from Telematica!");
  }
  console.log(
    "Location of the vehicle is (%d, %d)",
    locationResponse.body.location.lat,
    locationResponse.body.location.lng
  );
}
