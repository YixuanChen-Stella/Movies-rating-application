package com.stella.assignment2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v2")
public class MetadataController {

    private static final String BASE_URL = "http://169.254.169.254/latest/meta-data/";
    private static String instanceId;
    private static String availabilityZone;

    static {
        instanceId = fetchMetadata("instance-id");
        availabilityZone = fetchMetadata("placement/availability-zone");
    }

    private static String fetchMetadata(String path) {
        String urlString = BASE_URL + path;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(2000);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    return response.toString();
                }
            } else {
                System.out.println("Failed to fetch metadata IMDSv1: HTTP " + responseCode);
            }
        } catch (IOException e) {
            System.out.println("Failed to fetch metadata IMDSv1: " + e.getMessage());
        }
        return null;
    }

    @GetMapping("/metadata")
    public Map<String, String> getMetadata() {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("aws_instance_id", instanceId);
        metadata.put("aws_availability_zone_id", availabilityZone);
        return metadata;
    }
}

