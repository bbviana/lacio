package br.com.stilingue.lacio;

import static br.com.stilingue.lacio.Measurement.logTime;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author bbviana
 */
public class SilentJsoup {

    public static Document get(String url) {
        return logTime(() -> {
            try {
                return Jsoup.connect(url).ignoreContentType(true).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, "Jsoup.connect(%s)", url);
    }
}
