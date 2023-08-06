package ge.carapp.carappapi.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.security.SecureRandom;
import java.util.UUID;

@Slf4j
public class Secrets {
    private static final SecureRandom random = new SecureRandom();


    public static String base64(String username, String password) {
        String secret = "%s:%s".formatted(username, password);
        return Base64.encodeBase64String(secret.getBytes());
    }

    public static String decodeBase64(String encoded) {
        return new String(Base64.decodeBase64(encoded));
    }


    public static void main(String[] args) {
         log.info("new uuid {}", UUID.randomUUID());

        var res = base64("123", "456");
        log.info("encoded: {}", res);
        log.info("decoded: {}", decodeBase64(res));
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String generateNumericToken(int tokenLength) {
        StringBuilder sb = new StringBuilder(tokenLength);

        for (int i = 0; i < tokenLength; i++) {
            int randomInt = random.nextInt(10);
            sb.append(randomInt);
        }

        return sb.toString();
    }

}
