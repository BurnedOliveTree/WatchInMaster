package pw.edu.watchin.server.domain.video;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

@Getter
@AllArgsConstructor
public enum VideoQualityType {
    RESOLUTION_1080p(1920, 1080, "1080p"),
    RESOLUTION_720p(1280, 720, "720p"),
    RESOLUTION_480p(854, 480, "480p"),
    RESOLUTION_360p(640, 360, "360p"),
    RESOLUTION_240p(426, 240, "240p");

    private final int width;
    private final int height;
    private final String friendlyName;

    public static VideoQualityType base(int width, int height) {
        return IntStream.range(0, values().length)
            .map(i -> values().length - i - 1)
            .mapToObj(i -> values()[i])
            .filter(quality -> quality.width >= width && quality.height >= height)
            .findFirst()
            .orElse(values()[0]);
    }

    public Optional<VideoQualityType> next() {
        return Arrays.stream(values())
            .filter(value -> value.ordinal() == this.ordinal() + 1)
            .findAny();
    }
}
