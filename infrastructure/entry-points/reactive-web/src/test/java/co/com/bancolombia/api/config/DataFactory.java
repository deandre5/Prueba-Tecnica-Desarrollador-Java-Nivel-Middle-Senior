package co.com.bancolombia.api.config;

import co.com.bancolombia.api.RouteProperty;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DataFactory {

    public static RouteProperty buildRouteProperty() {
        return new RouteProperty(
                new RouteProperty.StatisticsRoute("/stats")
        );
    }

}
