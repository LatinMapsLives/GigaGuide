package ru.rogotovskiy.guide.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class TranslationService {

    private static final String TRANSLATE_URL = "https://translate.api.cloud.yandex.net/translate/v2/translate";
    private static final String API_KEY = "AQVNyhqjOPf4vCxbTMUZyIG-VQpIp1OBn7Tz0mhr"; // такой же, как в TTS
    private static final String FOLDER_ID = "b1g4as782c4bh4ppmk1m";

    public String translateToEnglish(String russianText) throws IOException {
        HttpPost request = new HttpPost(TRANSLATE_URL);
        request.setHeader("Authorization", "Api-Key " + API_KEY);
        request.setHeader("Content-Type", "application/json");

        String jsonBody = String.format("""
                {
                  "folderId": "%s",
                  "texts": ["%s"],
                  "targetLanguageCode": "en"
                }
                """, FOLDER_ID, escapeJson(russianText));

        request.setEntity(new StringEntity(jsonBody, StandardCharsets.UTF_8));

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(request)) {

            int status = response.getCode();
            if (status == 200) {
                String jsonResponse = new String(response.getEntity().getContent().readAllBytes());
                return extractTranslatedText(jsonResponse);
            } else {
                String error = new String(response.getEntity().getContent().readAllBytes());
                throw new IOException("Translation failed: " + error);
            }
        }
    }

    private String extractTranslatedText(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        JsonNode translations = root.path("translations");
        if (translations.isMissingNode() || !translations.isArray() || translations.isEmpty()) {
            throw new IOException("Invalid response: translations not found");
        }
        return translations.get(0).path("text").asText();
    }

    private String escapeJson(String text) {
        return text.replace("\"", "\\\"").replace("\n", "\\n");
    }
}
