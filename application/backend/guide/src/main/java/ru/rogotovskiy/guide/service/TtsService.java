package ru.rogotovskiy.guide.service;


import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TtsService {

    private static final String API_KEY = System.getenv("YANDEX_API_KEY");
    private static final String FOLDER_ID = "b1g4as782c4bh4ppmk1m";
    private static final String URL = "https://tts.api.cloud.yandex.net/speech/v1/tts:synthesize";

    public byte[] synthesizeText(String text) throws IOException {
        HttpPost request = new HttpPost(URL);
        request.setHeader("Authorization", "Api-Key " + API_KEY);
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");

        System.out.println(text);

        List<NameValuePair> params = List.of(
                new BasicNameValuePair("text", text),
                new BasicNameValuePair("lang", "ru-RU"),
                new BasicNameValuePair("voice", "jane"),
                new BasicNameValuePair("folderId", FOLDER_ID),
                new BasicNameValuePair("format", "lpcm"),
                new BasicNameValuePair("sampleRateHertz", "48000")
        );

        request.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(request)) {
            int status = response.getCode();
            if (status == 200) {
                byte[] result = response.getEntity().getContent().readAllBytes();
                System.out.println("Audio length: " + result.length + " bytes");
                return result;
            } else {
                String error = new String(response.getEntity().getContent().readAllBytes());
                throw new IOException("Error from Yandex API: " + error);
            }
        }
    }
}
