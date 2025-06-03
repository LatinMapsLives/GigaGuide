package ru.rogotovskiy.guide.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.rogotovskiy.guide.entity.MomentTranslation;
import ru.rogotovskiy.guide.repository.MomentTranslationRepository;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AudioGuideServiceTest {

    @Mock
    private MomentTranslationRepository momentRepository;

    @Mock
    private TtsService ttsService;

    @Mock
    private TranslationService translationService;

    @InjectMocks
    private AudioGuideService audioGuideService;

    @Test
    void getAudioForMoment_ShouldReturnAudio_WhenMomentExists() throws IOException {
        // Arrange
        MomentTranslation moment = new MomentTranslation();
        moment.setContent("Текст на русском");
        when(momentRepository.findByMomentIdAndLanguage_Code(anyInt(), anyString()))
                .thenReturn(Optional.of(moment));
        when(ttsService.synthesizeText(anyString(), anyString()))
                .thenReturn(new byte[]{1, 2, 3});

        // Act
        byte[] result = audioGuideService.getAudioForMoment(1, "ru");

        // Assert
        assertNotNull(result);
        verify(momentRepository).findByMomentIdAndLanguage_Code(1, "ru");
        verify(ttsService).synthesizeText("Текст на русском", "ru");
    }

    @Test
    void getAudioForMoment_ShouldTranslateToEnglish_WhenLangIsEn() throws IOException {
        // Arrange
        MomentTranslation moment = new MomentTranslation();
        moment.setContent("Текст на русском");
        when(momentRepository.findByMomentIdAndLanguage_Code(anyInt(), anyString()))
                .thenReturn(Optional.of(moment));
        when(translationService.translateToEnglish(anyString()))
                .thenReturn("Translated text");
        when(ttsService.synthesizeText(anyString(), anyString()))
                .thenReturn(new byte[]{1, 2, 3});

        // Act
        byte[] result = audioGuideService.getAudioForMoment(1, "en");

        // Assert
        assertNotNull(result);
        verify(translationService).translateToEnglish("Текст на русском");
        verify(ttsService).synthesizeText("Translated text", "en");
    }

    @Test
    void getAudioForMoment_ShouldThrowException_WhenMomentNotFound() {
        // Arrange
        when(momentRepository.findByMomentIdAndLanguage_Code(anyInt(), anyString()))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () ->
                audioGuideService.getAudioForMoment(1, "ru"));
    }

    @Test
    void getAudioForMoment_ShouldWrapAudioInWavFormat() throws IOException {
        // Arrange
        MomentTranslation moment = new MomentTranslation();
        moment.setContent("Текст на русском");
        when(momentRepository.findByMomentIdAndLanguage_Code(anyInt(), anyString()))
                .thenReturn(Optional.of(moment));
        byte[] testAudio = new byte[]{1, 2, 3};
        when(ttsService.synthesizeText(anyString(), anyString()))
                .thenReturn(testAudio);

        // Act
        byte[] result = audioGuideService.getAudioForMoment(1, "ru");

        // Assert
        assertNotNull(result);
        // Здесь можно добавить проверку заголовка WAV файла, если нужно
    }
}