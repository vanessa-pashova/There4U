package com.example.there4u.service.geo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.net.URL;

/**
 * A utility class for validating full postal addresses using the
 * Nominatim API provided by OpenStreetMap.
 * <p>
 * This class sends address queries to the public Nominatim endpoint
 * and checks if the given address returns a valid geographic result.
 * <p>
 * Nominatim does not require an API key but limits usage to
 * approximately 1 request per second. Usage should include a valid User-Agent.
 *
 * Usage example:
 * <pre>
 * boolean valid = OSMBatchAddressValidator.isValidAddress("Ivan Vazov 3, Burgas, Bulgaria");
 * </pre>
 */
public class OSMBatchAddressValidator {

    /**
     * Validates an address using the Nominatim OpenStreetMap API.
     * <p>
     * The method sends a GET request to the Nominatim API with the given address.
     * If the response contains geographic coordinates ("lat"), the address is considered valid.
     *
     * @param address a full address as a String (e.g., street, number, city)
     * @return true if the address is valid and recognized; false otherwise
     */
    public static boolean isValidAddress(String address) {
        try {
            String encodedAddress = URLEncoder.encode(address, "UTF-8");
            String urlStr = String.format(
                    "https://nominatim.openstreetmap.org/search?q=%s&format=json",
                    encodedAddress
            );

            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            //Set a required User-Agent header to comply with Nominatim usage policy
            connection.setRequestProperty("User-Agent", "StudentProjectValidator/1.0 4mi0800338@g.fmi.uni-sofia.bg");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();

            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            in.close();

            //If the response contains latitude, we assume the address is valid
            return response.toString().contains("\"lat\":");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}