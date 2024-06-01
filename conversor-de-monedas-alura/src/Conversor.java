import java.util.Map;

public record Conversor(
                        String base_code,
                        Map<String, Double> conversion_rates) {
}
