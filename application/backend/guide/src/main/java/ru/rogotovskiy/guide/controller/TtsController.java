package ru.rogotovskiy.guide.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.rogotovskiy.guide.service.AudioGuideService;

import java.io.IOException;

@RestController
@RequestMapping("/api/guide")
@RequiredArgsConstructor
public class TtsController {

    private final AudioGuideService audioGuideService;

    @GetMapping(produces = "audio/wav")
    public ResponseEntity<byte[]> getAudio(@RequestParam Integer id) throws IOException {
        byte[] audio = audioGuideService.getAudioForMoment(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=guide.wav")
                .contentType(MediaType.parseMediaType("audio/wav"))
                .body(audio);
    }
}
