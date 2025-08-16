package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

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

        map1.forEach((key, value) -> {
            if (!map1.containsKey(key)) {
                result.append("  - ").append(key).append(": ").append(value).append("\n");
            } else if (!value.equals(map2.get(key))) {
                result.append("  - ").append(key).append(": ").append(value).append("\n");
                result.append("  + ").append(key).append(": ").append(map2.get(key)).append("\n");
            }
        });

        map2.forEach((key, value) -> {
            if (!map2.containsKey(key)) {
                result.append("  + ").append(key).append(": ").append(value).append("\n");
            }
        });

        return result.append("}").toString();
    }
}
