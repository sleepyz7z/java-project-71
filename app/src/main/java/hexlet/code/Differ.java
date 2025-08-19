package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Objects;

public class Differ {
    public static String generate(String filePath1, String filePath2, String format) throws Exception {
        Map<String, Object> data1 = parseFile(filePath1);
        Map<String, Object> data2 = parseFile(filePath2);
        return formatDiff(data1, data2, format);
    }
    public static Map<String, Object> parseFile(String filePath) throws Exception {
        Path path = Paths.get(filePath).toAbsolutePath().normalize();
        if (!Files.exists(path)) {
            throw new Exception("File not found: " + path);
        }
        String content = Files.readString(path);
        return new ObjectMapper().readValue(content, new TypeReference<>() {});
    }

    public static String formatDiff(Map<String, Object> map1, Map<String, Object> map2, String format) {
        if (!"stylish".equals(format)) {
            throw new UnsupportedOperationException("Unsupported format: " + format);
        }
        StringBuilder result = new StringBuilder("{\n");
        TreeSet<String> allKeys = new TreeSet<>();
        allKeys.addAll(map1.keySet());
        allKeys.addAll(map2.keySet());

        for (String key : allKeys) {
            boolean inFirst = map1.containsKey(key);
            boolean inSecond = map2.containsKey(key);
            Object value1 = inFirst ? map1.get(key) : null;
            Object value2 = inSecond ? map2.get(key) : null;

            if (inFirst && inSecond && Objects.equals(value1, value2)) {
                result.append("    ").append(key).append(": ").append(value1).append("\n");
            } else {
                if (inFirst) {
                    result.append("  - ").append(key).append(": ").append(value1).append("\n");
                }
                if (inSecond) {
                    result.append("  + ").append(key).append(": ").append(value2).append("\n");
                }
            }
        }
        return result.append("}").toString();
    }
}
