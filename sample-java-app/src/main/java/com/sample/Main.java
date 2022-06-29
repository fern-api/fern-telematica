package com.sample;

import com.telematica.AuthHeader;
import com.telematica.client.vehicleData.VehicleDataServiceClient;
import com.telematica.services.vehicleData.GetRangeSocResponse;
import com.telematica.services.vehicleData.GetStaticAttributesResponse;
import com.telematica.types.vehicleData.VehicleId;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Optional;

public class Main {

    // From Telematica API Docs
    public static final VehicleId FAKE_VEHICLE_ID = VehicleId.valueOf("61fe4a188a4c4d1ef9ba0b62");

    public static void main(String... args) {
        Dotenv dotenv = Dotenv.configure()
                .directory(".")
                .load();
        AuthHeader authHeader = AuthHeader.valueOf(dotenv.get("TELEMATICA_TOKEN"));

        VehicleDataServiceClient vehicleDataServiceClient =
                VehicleDataServiceClient.getClient("https://telematica-v2.herokuapp.com/v1");

        GetRangeSocResponse getRangeSocResponse =
                vehicleDataServiceClient.getRangeSoc(authHeader, Optional.of(FAKE_VEHICLE_ID));
        System.out.printf("Received range: %s and soc: %d", getRangeSocResponse.range(), getRangeSocResponse.soc());

        GetStaticAttributesResponse getStaticAttributesResponse =
                vehicleDataServiceClient.getStaticAttributes(authHeader, Optional.of(FAKE_VEHICLE_ID));
        System.out.printf("The make of the vehicle is %s", getStaticAttributesResponse.make());
    }
}
