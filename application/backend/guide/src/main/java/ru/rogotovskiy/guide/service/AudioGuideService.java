package ru.rogotovskiy.guide.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.guide.entity.Moment;
import ru.rogotovskiy.guide.repository.MomentRepository;
import ru.rogotovskiy.guide.util.WavUtil;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AudioGuideService {

    private final MomentRepository momentRepository;
    private final TtsService ttsService;
    private final TranslationService translationService;

    public byte[] getAudioForMoment(Integer momentId, String lang) throws IOException {
        Moment moment = momentRepository.findById(momentId)
                .orElseThrow(() -> new EntityNotFoundException("Moment not found"));

        String content = moment.getContent();
        if ("en".equalsIgnoreCase(lang)) {
            content = translationService.translateToEnglish(content);
        }
        byte[] lpcmAudio = ttsService.synthesizeText(content, lang);
        return WavUtil.wrapLpcmInWav(lpcmAudio, 48000, 1);
    }
}
