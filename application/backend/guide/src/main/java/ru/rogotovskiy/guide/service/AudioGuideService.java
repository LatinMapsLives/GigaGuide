package ru.rogotovskiy.guide.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.guide.entity.MomentTranslation;
import ru.rogotovskiy.guide.repository.MomentTranslationRepository;
import ru.rogotovskiy.guide.util.WavUtil;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AudioGuideService {

    private final MomentTranslationRepository momentRepository;
    private final TtsService ttsService;
    private final TranslationService translationService;

    public byte[] getAudioForMoment(Integer momentId, String lang) throws IOException {
        MomentTranslation moment = momentRepository.findByMomentIdAndLanguage_Code(momentId, lang)
                .orElseThrow(() -> new EntityNotFoundException("Moment not found"));

        String content = moment.getContent();
        if ("en".equalsIgnoreCase(lang)) {
            content = translationService.translateToEnglish(content);
        }
        byte[] lpcmAudio = ttsService.synthesizeText(content, lang);
        return WavUtil.wrapLpcmInWav(lpcmAudio, 48000, 1);
    }
}
