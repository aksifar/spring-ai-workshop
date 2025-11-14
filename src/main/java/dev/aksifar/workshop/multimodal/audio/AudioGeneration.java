package dev.aksifar.workshop.multimodal.audio;

import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.ai.openai.audio.speech.SpeechResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AudioGeneration {

    private final OpenAiAudioSpeechModel audioModel;

    public AudioGeneration(OpenAiAudioSpeechModel audioModel) {
        this.audioModel = audioModel;
    }

    @GetMapping("/speak")
    public ResponseEntity<byte[]> generateSpeech(@RequestParam(defaultValue = "Its a great time to be an AI Engineer") String text) {
        var options = OpenAiAudioSpeechOptions.builder()
                .model("tts-1-hd") // or "tts-1"
                .voice(OpenAiAudioApi.SpeechRequest.Voice.ALLOY) // "alloy", "bella", "chris", "dario", "eva", "fiona", "greg", "hannah"
                .responseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                .speed(1.0f) // Speed multiplier (0.5x to 2.0x)
                .build();

        SpeechPrompt speechPrompt = new SpeechPrompt(text, options);
        SpeechResponse  speechResponse = audioModel.call(speechPrompt);

        byte[] audioData = speechResponse.getResult().getOutput();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "audio/mpeg")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"speech.mp3\"")
                .body(audioData);
    }
}
