package com.sample;

import com.telematica.AuthHeader;
import com.telematica.client.vehicleData.VehicleDataServiceClient;
import com.telematica.client.vehicleData.VehicleDataTestServiceClient;
import com.telematica.types.vehicleData.LocationResponse;
import com.telematica.types.vehicleData.SocResponse;
import com.telematica.types.vehicleData.StaticAttributes;
import com.telematica.types.vehicleData.VehicleId;
import io.github.cdimascio.dotenv.Dotenv;

public class MyApp {

     private static final Dotenv DOTENV = Dotenv.configure()
             .directory("../")
             .load();

    public static void main(String... args) {
        demoWithTestingService();
    }

    private static void demoWithTestingService() {
        AuthHeader authHeader = AuthHeader.valueOf(DOTENV.get("TELEMATICA_TEST_TOKEN"));
        VehicleDataTestServiceClient testClient =
                VehicleDataTestServiceClient.getClient("https://telematica-v2.herokuapp.com/v1");

        VehicleId vehicleId = VehicleId.valueOf("61fe4a188a4c4d1ef9ba0b62");

        SocResponse socResponse = testClient.getSOCTest(
                authHeader, vehicleId);
        System.out.printf("Soc of the vehicle is %d\n", socResponse.soc());

        LocationResponse locationResponse = testClient.getLocationTest(authHeader, vehicleId);
        System.out.printf("Location of the vehicle is (%f, %f)\n",
                locationResponse.location().lat(), locationResponse.location().lng());

        StaticAttributes staticAttributes =
                testClient.getStaticAttributesTest(authHeader, vehicleId);
        System.out.printf("The vehicle has model %s which was made in the year %s.\n",
                staticAttributes.model(), staticAttributes.year());
    }

    private static void demoWithRealService() {
        AuthHeader authHeader = AuthHeader.valueOf(DOTENV.get("TELEMATICA_TEST_TOKEN"));
        VehicleDataServiceClient client =
                VehicleDataServiceClient.getClient("https://telematica-v2.herokuapp.com/v1");

        // Change me to point to a real vehicle id!
        VehicleId vehicleId = VehicleId.valueOf("REAL_VEHICLE");

        SocResponse socResponse = client.getSOC(
                authHeader, vehicleId);
        System.out.printf("Soc of the vehicle is %d\n", socResponse.soc());

        LocationResponse locationResponse = client.getLocation(authHeader, vehicleId);
        System.out.printf("Location of the vehicle is (%f, %f)\n",
                locationResponse.location().lat(), locationResponse.location().lng());

        StaticAttributes staticAttributes =
                client.getStaticAttributes(authHeader, vehicleId);
        System.out.printf("The vehicle has model %s which was made in the year %s.\n",
                staticAttributes.model(), staticAttributes.year());
    }
}
