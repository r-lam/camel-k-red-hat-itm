# Red Hat Integrations - Camel K

Este repositorio contiene ejemplos de integraciones listas para ser usadas en un ambiente de K8s u OpenShift.

## Instale la Línea de Comandos Kamel

Antes de empezar debe de tener listo 'kamel' para poder hacer deploy de los ejemplos que aquí se tienen.

Si aún no tiene instalado kamel, puede visitar el repo oficial y descargar la carpeta según su Sistema Operativo.

[Camel K CLI](https://github.com/apache/camel-k/releases)

## Tooling for Apache Camel K

Instale la extensión de Camel K para VS Code desde el menú de Extensiones.

## Kubernetes/OpenShift Login

Debe logearse a su ambiente antes, ya sea desde **kubectl** o mediante **oc login** para OpenShift.

## Cluster de Kafka

Prepare un cluster de Kafka, esto en OpenShift se puede hacer mediante el operador **AMQ Streams**, creando una nueva instancia de Kafka, asegúrese de anotar el nombre del cluster.

## OpenAPI - Kafka [Integración]

En este escenario, ocupamos una API con especificacion OpenAPI V3.0.2 para poder consumir mensajes de un topico de un cluster de Kafka, mediante un http GET request.
A la vez, poder producir mensajes mediante un http POST request.

En el archivo application.properties se define el cluster a ocupar así como cualquier otra configuración que se desee modificar.

Para correr el ejemplo de OpenAPI-Kafka abra una terminal de VS Code, dirijase a la carpeta de 'openapi' y ejecute:

## En Linux/MacOS
```kamel run openapi/openapi.java --name api-kafka --open-api openapi/records.yaml```

## En Windows - ASEGURARSE DE ESTAR EN LA CARPETA OPENAPI
```kamel run openapi.java --name api-kafka --open-api records.yaml```

Podra observar como el operador de Camel K se encarga de levantar el contenedor y todos los recursos necesarios. Desde VS Code puede seguir los logs, dando click derecho a la integracion y click en **Follow Integration logs**.

## Probando Endpoints

**GET**
Desde una terminal ejecute:

```curl https://api-kafka.{openshift/k8s.ip}/ -s```

**POST**
Desde una terminal ejecute:

```curl https://api-kafka.{openshift/k8s.ip}/ --header "Content-Type: application/json" --request POST --data CamelK! -s```