package co.com.bancolombia.api.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.cors.reactive.CorsWebFilter;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CorsConfigTest {
    private CorsConfig corsConfig;

    @BeforeEach
    void setUp() {
        corsConfig = new CorsConfig();
    }

    @Test
    void corsWebFilterShouldBeConfiguredCorrectly() {
        String allowedOriginsValue = "http://localhost:8080,https://www.ejemplo.com";

        CorsWebFilter corsWebFilter = corsConfig.corsWebFilter(allowedOriginsValue);

        assertNotNull(corsWebFilter, "CorsWebFilter no debería ser nulo");

    }

    @Test
    void corsWebFilterWithSingleOriginShouldBeConfiguredCorrectly() {
        String allowedOriginsValue = "https://single-origin.com";

        CorsWebFilter corsWebFilter = corsConfig.corsWebFilter(allowedOriginsValue);
        assertNotNull(corsWebFilter, "CorsWebFilter no debería ser nulo para un solo origen");
    }

    @Test
    void corsWebFilterWithEmptyOriginsShouldHandleGracefully() {
        String allowedOriginsValue = "";
        CorsWebFilter corsWebFilter = corsConfig.corsWebFilter(allowedOriginsValue);

        assertNotNull(corsWebFilter, "CorsWebFilter no debería ser nulo incluso con orígenes vacíos");
    }
}
