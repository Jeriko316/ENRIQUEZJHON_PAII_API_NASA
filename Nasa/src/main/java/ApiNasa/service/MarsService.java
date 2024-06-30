package ApiNasa.service;

import ApiNasa.ConsumerApi.HttpClient;
import ApiNasa.modelo.Mars;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MarsService {

    private final List<Mars> photos;

    public MarsService(String apiUrl) throws Exception {
        String jsonResponse = HttpClient.get(apiUrl);
        System.out.println("JSON Response: " + jsonResponse);  // Log de depuración
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonResponse);
        JsonNode photosNode = rootNode.get("photos");

        List<Mars> photos = mapper.readerForListOf(Mars.class).readValue(photosNode);
        this.photos = Collections.unmodifiableList(photos);
    }

    public List<Mars> getPhotos() {
        return photos;
    }

    public List<Mars> filterBySol(String sol) {
        return photos.stream()
                .filter(photo -> String.valueOf(photo.getSol()).equals(sol))
                .collect(Collectors.toList());
    }

    public List<Mars> filterByEarthDate(String earthDate) {
        return photos.stream()
                .filter(photo -> photo.getEarthDate().equals(earthDate))
                .collect(Collectors.toList());
    }
}
