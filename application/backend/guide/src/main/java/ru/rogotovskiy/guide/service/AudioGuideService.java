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

    public byte[] getAudioForMoment(Integer momentId) throws IOException {
        Moment moment = momentRepository.findById(momentId)
                .orElseThrow(() -> new EntityNotFoundException("Moment not found"));

        byte[] lpcmAudio = ttsService.synthesizeText(moment.getContent());
        return WavUtil.wrapLpcmInWav(lpcmAudio, 48000, 1);
    }
}
