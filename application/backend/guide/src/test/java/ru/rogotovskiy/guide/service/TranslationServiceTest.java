package ru.rogotovskiy.guide.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TranslationServiceTest {

    @InjectMocks
    private TranslationService translationService;

    @Test
    void extractTranslatedText_ShouldReturnText_WhenValidJson() throws IOException {
        String json = """
            {
              "translations": [
                {
                  "text": "Hello",
                  "detectedLanguageCode": "ru"
                }
              ]
            }
            """;

        String result = translationService.extractTranslatedText(json);
        assertEquals("Hello", result);
    }

    @Test
    void extractTranslatedText_ShouldThrow_WhenInvalidJson() {
        String invalidJson = "invalid json";

        assertThrows(IOException.class, () ->
                translationService.extractTranslatedText(invalidJson));
    }

    @Test
    void escapeJson_ShouldEscapeSpecialChars() {
        String input = "Text with \"quotes\" and\nnew line";
        String expected = "Text with \\\"quotes\\\" and\\nnew line";

        assertEquals(expected, translationService.escapeJson(input));
    }
}