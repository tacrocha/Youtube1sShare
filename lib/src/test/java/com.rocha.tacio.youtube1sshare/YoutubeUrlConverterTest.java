package com.rocha.tacio.youtube1sshare;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static com.rocha.tacio.youtube1sshare.YoutubeUrlConverter.DEFAULT_URL;
import static org.assertj.core.api.Assertions.assertThat;

public class YoutubeUrlConverterTest {

    private static final YoutubeUrlConverter converter = new YoutubeUrlConverter();

    /*
    When a youtu.be URL is shared: https://youtu.be/785j0LMbJ5U
    Youtube1s URL should be: https://www.youtube1s.com/watch?v=785j0LMbJ5U
     */
    @Test
    public void convertsYoutuDotBe() throws MalformedURLException, URISyntaxException {
        String originalUrl1 = "https://youtu.be/KJTPDHHGm2w";
        String youtube1sUrl1 = "https://youtube1s.com/watch?v=KJTPDHHGm2w";
        assertThat(converter.convert(originalUrl1)).isEqualTo(youtube1sUrl1);

        String originalUrl2 = "https://www.youtu.be/785j0LMbJ5U";
        String youtube1sUrl2 = "https://youtube1s.com/watch?v=785j0LMbJ5U";
        assertThat(converter.convert(originalUrl2)).isEqualTo(youtube1sUrl2);

    }

    /*
    When a youtube.com URL is shared: https://www.youtube.com/watch?v=tCAwGsz6JfI
    Youtube1s URL should be: https://youtube1s.com/watch?v=tCAwGsz6JfI
     */
    @Test
    public void convertsYoutubeDotCom() throws MalformedURLException, URISyntaxException {
        String originalUrl1 = "https://youtube.com/watch?v=tCAwGsz6JfI&foo=bar";
        String youtube1sUrl = "https://youtube1s.com/watch?v=tCAwGsz6JfI";
        assertThat(converter.convert(originalUrl1)).isEqualTo(youtube1sUrl);

        String originalUrl2 = "https://www.youtube.com/watch?v=tCAwGsz6JfI";
        assertThat(converter.convert(originalUrl2)).isEqualTo(youtube1sUrl);
    }

    @Test
    public void conversionIsCaseInsensitive() {
        String originalUrl1 = "HTTPS://YOUTUBE.COM/WATCH?v=tCAwGsz6JfI&foo=bar";
        String youtube1sUrl = "https://youtube1s.com/watch?v=tCAwGsz6JfI";
        assertThat(converter.convert(originalUrl1)).isEqualTo(youtube1sUrl);
    }

    @Test
    public void unsupportedUrlReturnsDefaultUrl() throws MalformedURLException, URISyntaxException {
        String nonYoutubeUrl = "https://facebook.com";
        assertThat(converter.convert(nonYoutubeUrl)).isEqualTo(DEFAULT_URL);

        String missingPathUrl = "https://youtu.be";
        assertThat(converter.convert(missingPathUrl)).isEqualTo(DEFAULT_URL);

        String missingParamVUrl = "https://youtube.com/watch?foo=bar";
        assertThat(converter.convert(missingParamVUrl)).isEqualTo(DEFAULT_URL);

        String wrongPathUrl = "https://youtube.com/WRONGPATH?v=tCAwGsz6JfI";
        assertThat(converter.convert(wrongPathUrl)).isEqualTo(DEFAULT_URL);
    }

    @Test
    public void urlAndUriExceptionsReturnDefaultUrl() throws MalformedURLException, URISyntaxException {
        String malformedUrl = "malformed://youtu.be/jkjkjkj";
        assertThat(converter.convert(malformedUrl)).isEqualTo(DEFAULT_URL);
    }

}
