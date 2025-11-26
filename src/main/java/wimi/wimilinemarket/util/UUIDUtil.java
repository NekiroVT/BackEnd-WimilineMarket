package wimi.wimilinemarket.util;

import java.util.UUID;

public class UUIDUtil {


    public static UUID parseFlexibleUUID(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("El UUID proporcionado está vacío o es nulo");
        }

        raw = raw.trim().toLowerCase();


        if (!raw.contains("-")) {
            raw = raw.replaceAll(
                    "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
                    "$1-$2-$3-$4-$5"
            );
        }

        return UUID.fromString(raw);
    }
}
