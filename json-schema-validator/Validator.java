// camel-k: language=java name=json-schema-validator config=configmap:validator-properties trait=route.enabled=true resource=configmap:json-schema dependency=camel-json-validator

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.PropertyInject;
import org.apache.camel.Exchange;
import org.apache.camel.component.jsonvalidator.JsonValidationException;

public class Validator extends RouteBuilder {

    @PropertyInject("ENDPOINT")
    private String ENDPOINT;

    private static final String REST_ENDPOINT_ECHO= "{{ENDPOINT}}?httpClient.connectTimeout=1000" +
            "&bridgeEndpoint=true" +
            "&copyHeaders=true" +
            "&connectionClose=true";

    @Override
    public void configure() throws Exception {

        onException(JsonValidationException.class)
            .handled(true)
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
            .setHeader(Exchange.CONTENT_TYPE, simple("text/plain"))
            .log("Body: ${body}")
            .setBody(simple("Error: Invalid JSON Schema"));
        
        from("direct:echoServiceUrl")
            .to("json-validator:file:/etc/camel/resources/json-schema/schema.json")
            .to("https://" + REST_ENDPOINT_ECHO)
                .log("Response: " + "${body}")
                .convertBodyTo(String.class)
        .end();
        
        rest()
            .post("/echo").enableCORS(true).route()
            .to("direct:echoServiceUrl");
    }
}