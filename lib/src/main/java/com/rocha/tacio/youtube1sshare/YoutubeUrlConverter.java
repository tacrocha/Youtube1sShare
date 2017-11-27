package com.rocha.tacio.youtube1sshare;

import org.apache.http.client.utils.URIBuilder;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class YoutubeUrlConverter {

    // default URL
    public static final String DEFAULT_URL = "https://youtubemultidownloader.com";
    public static final String YOUTU_BE = "youtu.be";
    public static final String YOUTUBE1S_COM = "youtube1s.com";
    public static final String WATCH = "/watch";
    public static final String PARAM_V = "v";
    public static final String YOUTUBE_COM = "youtube.com";
    public static final String WWW_YOUTUBE_COM = "www.youtube.com";
    public static final String WWW_YOUTU_BE = "www.youtu.be";

    private URIBuilder uriBuilder;

    public YoutubeUrlConverter() {
        this.uriBuilder = new URIBuilder();
    }

    public String convert(String originalUrl) {
        String youtube1sUrl = DEFAULT_URL;
        try {
            URL inUrl = new URL(originalUrl);
            URL lowerCaseInUrl = new URL(originalUrl.toLowerCase());
            switch (lowerCaseInUrl.getHost()) {
                case YOUTU_BE:
                case WWW_YOUTU_BE:
                    validateYoutuDotBe(lowerCaseInUrl);
                    youtube1sUrl = uriBuilder
                            .setScheme(lowerCaseInUrl.getProtocol())
                            .setHost(YOUTUBE1S_COM)
                            .setPath(WATCH)
                            .setParameter(PARAM_V, removeLeadingSlash(inUrl.getPath()))
                            .build().toURL().toString();
                    break;
                case YOUTUBE_COM:
                case WWW_YOUTUBE_COM:
                    validateYoutubeDotCom(lowerCaseInUrl);
                    youtube1sUrl = uriBuilder
                            .setScheme(lowerCaseInUrl.getProtocol())
                            .setHost(YOUTUBE1S_COM)
                            .setPath(WATCH)
                            .setParameter(PARAM_V, getParamV(inUrl.getQuery()))
                            .build().toURL().toString();
            }
        } catch (MalformedURLException | URISyntaxException | InvalidYoutubeUrlException e) {
            // Do nothing. Default URL is returned.
        }
        return youtube1sUrl;
    }

    private void validateYoutuDotBe(URL inUrl) throws InvalidYoutubeUrlException {
        if (inUrl.getPath().equals("")) {
            throw new InvalidYoutubeUrlException();
        }
    }

    private void validateYoutubeDotCom(URL inUrl) throws InvalidYoutubeUrlException {
        if (!inUrl.getPath().equals(WATCH) || getParamV(inUrl.getQuery()).equals("")) {
            throw new InvalidYoutubeUrlException();
        }
    }

    private String getParamV(String query) {
        String paramV = "";
        String[] queryKVPs = query.split("&");
        for (String kvp : queryKVPs) {
            if (kvp.startsWith(PARAM_V + "=")) {
                paramV = kvp.split("=")[1];
            }
        }
        return paramV;
    }

    private String removeLeadingSlash(String path) {
        return path.substring(1);
    }

    private class InvalidYoutubeUrlException extends Throwable {
        private String msg;

        public InvalidYoutubeUrlException() {
            msg = "Youtube URL is invalid";
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

}
