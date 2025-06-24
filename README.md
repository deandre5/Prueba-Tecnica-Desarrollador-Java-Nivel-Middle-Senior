# Proyecto Base Implementando Clean Architecture

## Antes de Iniciar

Empezaremos por explicar los diferentes componentes del proyectos y partiremos de los componentes externos, continuando con los componentes core de negocio (dominio) y por último el inicio y configuración de la aplicación.

Lee el artículo [Clean Architecture — Aislando los detalles](https://medium.com/bancolombia-tech/clean-architecture-aislando-los-detalles-4f9530f35d7a)

# Arquitectura

![Clean Architecture](https://miro.medium.com/max/1400/1*ZdlHz8B0-qu9Y-QO3AXR_w.png)

## Domain

Es el módulo más interno de la arquitectura, pertenece a la capa del dominio y encapsula la lógica y reglas del negocio mediante modelos y entidades del dominio.

## Usecases

Este módulo gradle perteneciente a la capa del dominio, implementa los casos de uso del sistema, define lógica de aplicación y reacciona a las invocaciones desde el módulo de entry points, orquestando los flujos hacia el módulo de entities.

## Infrastructure

### Helpers

En el apartado de helpers tendremos utilidades generales para los Driven Adapters y Entry Points.

Estas utilidades no están arraigadas a objetos concretos, se realiza el uso de generics para modelar comportamientos
genéricos de los diferentes objetos de persistencia que puedan existir, este tipo de implementaciones se realizan
basadas en el patrón de diseño [Unit of Work y Repository](https://medium.com/@krzychukosobudzki/repository-design-pattern-bc490b256006)

Estas clases no puede existir solas y debe heredarse su compartimiento en los **Driven Adapters**

### Driven Adapters

Los driven adapter representan implementaciones externas a nuestro sistema, como lo son conexiones a servicios rest,
soap, bases de datos, lectura de archivos planos, y en concreto cualquier origen y fuente de datos con la que debamos
interactuar.

### Entry Points

Los entry points representan los puntos de entrada de la aplicación o el inicio de los flujos de negocio.

## Application

Este módulo es el más externo de la arquitectura, es el encargado de ensamblar los distintos módulos, resolver las dependencias y crear los beans de los casos de use (UseCases) de forma automática, inyectando en éstos instancias concretas de las dependencias declaradas. Además inicia la aplicación (es el único módulo del proyecto donde encontraremos la función “public static void main(String[] args)”.

**Los beans de los casos de uso se disponibilizan automaticamente gracias a un '@ComponentScan' ubicado en esta capa.**

## Configuracion basica antes de la ejecucion del Microservicio
1. Tener instalado docker o podman
2. Iniciar mediante Docker o Podman el doker-compose que encontramos en deployment/docker-compose.yml
3. Crear un exchange dentro de rabbit llamado domainEvents
4. Crear una cola llamada event.stats.validated
5. Crear un bind dentro de domainEvents a la cola event.stats.validated
6. Crear una tabla en DynamoDb para almacenar los datos con el siguiente comando
   aws --endpoint-url=http://localhost:8000 dynamodb create-table \
   --table-name mueblesStats \
   --attribute-definitions \
   AttributeName=hash,AttributeType=S \
   AttributeName=timeStamp,AttributeType=S \
   --key-schema \
   AttributeName=hash,KeyType=HASH \
   AttributeName=timeStamp,KeyType=RANGE \
   --provisioned-throughput \
   ReadCapacityUnits=5,WriteCapacityUnits=5

## Requisitos para ejecucion del MS
1. crea una variable de entorno llamada SPRING_PROFILES_ACTIVE=local

## Como ejecutar las pruebas unitarias?
1. mediante el comando "./gradlew clean test jacocoMergedReport" podemos ejecutar todas las pruebas unitarias del MS. este ademas de ejecutarlas nos generara un reporte con el % de cobertura
2. despues de terminar la ejecucion encontraremos el resultado de las pruebas en un informe que podremos ver en la carpeta build/reports/jacocoMergedReport/html/index.html

**Curl para la correcta ejecucion del Microservicio**

curl --location 'http://localhost:8092/api/v1/stats' \
--header 'transaction-code: 0208' \
--header 'channel: APP' \
--header 'Content-Type: application/json' \
--data '{
"data": {
"statistics": {
"totalContactoClientes": 250,
"motivoReclamo": 25,
"motivoGarantia": 10,
"motivoDuda": 100,
"motivoCompra": 100,
"motivoFelicitaciones": 7,
"motivoCambio": 8,
"hash": "5484062a4be1ce5645eb414663e14f59"
}
}
}'

**ejemplo de body**
```aidl
{
    "data": {
        "statistics": {
            "totalContactoClientes": 250,
            "motivoReclamo": 25,
            "motivoGarantia": 10,
            "motivoDuda": 100,
            "motivoCompra": 100,
            "motivoFelicitaciones": 7,
            "motivoCambio": 8,
            "hash": "5484062a4be1ce5645eb414663e14f59"
        }
    }
}
```

**Ejemplo de una respuesta correcta**
```aidl
{
    "data": {
        "statistics": {
            "timeStamp": "2025-06-24T04:45:04.967028400Z"
        }
    }
}
```


**Ejemplo de una respuesta con error**
```aidl
{
    "errors": [
        {
            "reason": "An error occurred on request, no hash field was sent",
            "domain": "/stats",
            "code": "CCB0016",
            "message": "An error occurred on request, no hash field was sent"
        }
    ]
}
```


