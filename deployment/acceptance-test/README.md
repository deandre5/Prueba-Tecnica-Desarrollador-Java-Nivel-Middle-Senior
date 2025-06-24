#  Proyecto Base de Karate para pruebas de integraci�n en AcceptanceTest - REST, GraphQL, SOAP
_Karate es una herramienta de c�digo abierto que combina la automatizaci�n de pruebas de API, simulacros , pruebas de rendimiento e incluso la automatizaci�n de la interfaz de usuario en un marco �nico y unificado . La sintaxis BDD popularizada por Cucumber es un lenguaje neutro y f�cil incluso para los no programadores. Las afirmaciones y los informes HTML est�n integrados y puede ejecutar pruebas en paralelo para aumentar la velocidad._

_Si est� familiarizado con Cucumber / Gherkin, la gran diferencia aqu� es que no necesita escribir c�digo extra de "pegamento" o "definiciones de pasos" de Java._

**RECOMENDACION !!!**: Visitar la documentaci�n oficial para obtener todas las ventajas de este potencial framework: https://github.com/intuit/karate
## Comenzando

### Instalaci�n ?

**IMPORTANTE**: 
Aqu� se detalla la estructura que debe guiar las pruebas con Karate, es un ejemplo:

```
src
??? test
    ??? java
    ?   ??? co.com.bancolombia
    ?       ??? TestParallel.java
    ?       ??? utils
    ?           ??? ValidatorTestUtils.java
    ??? resources
        ??? co.com.bancolombia
        ?   ??? karate
            ?   ??? save_stats.feature
        ??? karate-config.js
        ??? logback-test.xml
```

- TestParallel -> Clase general en java que ejecuta los TESTS de karate en Paralelo y tambien genera el reporte de dichos TESTS en formato json que luego se convierte en reporte cucumber

## Ejecutando las pruebas?

ejecutar todas las pruebas disponibles
```gradle
gradle test --tests TestParallel
```