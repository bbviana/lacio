package br.com.stilingue.lacio;

import static java.lang.String.format;

import java.util.function.Supplier;

/**
 * @author bbviana
 */
public class Measurement {

    public static <T> T logTime(Supplier<T> method, String identifier, Object... args) {
        long start = System.currentTimeMillis();
        T result = method.get();
        long elapsed = System.currentTimeMillis() - start;

        String formattedIdentifier = format(identifier, args);
        System.out.printf("[%s]: executado em %d ms\n", formattedIdentifier, elapsed);
        return result;
    }
}
