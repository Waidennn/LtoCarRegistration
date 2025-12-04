//CodeGenerator. java

package utils;

import java.util.UUID;

public class CodeGenerator {
    public static String generateDriverCode() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}