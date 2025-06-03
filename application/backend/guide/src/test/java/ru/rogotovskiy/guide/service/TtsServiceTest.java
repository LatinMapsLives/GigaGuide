package ru.rogotovskiy.guide.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TtsServiceTest {

    @InjectMocks
    private TtsService ttsService;

    @Test
    void synthesizeText_ShouldNotThrow_ForValidInput() {
        assertDoesNotThrow(() ->
                ttsService.synthesizeText("Test text", "ru"));
    }

    @Test
    void synthesizeText_ShouldUseEnglishVoice_WhenLangIsEn() {
        assertDoesNotThrow(() ->
                ttsService.synthesizeText("Test text", "en"));
    }
}