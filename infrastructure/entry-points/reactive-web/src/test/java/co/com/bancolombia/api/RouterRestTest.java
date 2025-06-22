package co.com.bancolombia.api;

import co.com.bancolombia.api.config.DataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class RouterRestTest {

    private RouteProperty routeProperty;

    @Mock
    private Handler handler;

    @BeforeEach
    void init() {
        routeProperty = DataFactory.buildRouteProperty();
    }

    @Test
    void shouldCreateRouter() {
        RouterRest router = new RouterRest(routeProperty);
        assertNotNull(router.routerFunction(handler));
    }

}
