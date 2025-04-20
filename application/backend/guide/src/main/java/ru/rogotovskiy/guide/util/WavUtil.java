package ru.rogotovskiy.guide.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class WavUtil {

    public static byte[] wrapLpcmInWav(byte[] rawData, int sampleRate, int channels) {
        int byteRate = sampleRate * channels * 2;
        int totalDataLen = rawData.length + 36;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write("RIFF".getBytes(StandardCharsets.US_ASCII));
            out.write(intToLittleEndian(totalDataLen));
            out.write("WAVE".getBytes(StandardCharsets.US_ASCII));

            out.write("fmt ".getBytes(StandardCharsets.US_ASCII));
            out.write(intToLittleEndian(16));
            out.write(shortToLittleEndian((short) 1));
            out.write(shortToLittleEndian((short) channels));
            out.write(intToLittleEndian(sampleRate));
            out.write(intToLittleEndian(byteRate));
            out.write(shortToLittleEndian((short) (channels * 2)));
            out.write(shortToLittleEndian((short) 16));

            out.write("data".getBytes(StandardCharsets.US_ASCII));
            out.write(intToLittleEndian(rawData.length));
            out.write(rawData);
        } catch (IOException e) {
            throw new RuntimeException("Error creating WAV", e);
        }
        return out.toByteArray();
    }

    private static byte[] intToLittleEndian(int value) {
        return new byte[]{
                (byte) (value & 0xff),
                (byte) ((value >> 8) & 0xff),
                (byte) ((value >> 16) & 0xff),
                (byte) ((value >> 24) & 0xff)
        };
    }

    private static byte[] shortToLittleEndian(short value) {
        return new byte[]{
                (byte) (value & 0xff),
                (byte) ((value >> 8) & 0xff)
        };
    }
}
