// Camel-K, Java

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

public class openapi extends RouteBuilder{
    private static final List<String> kfkrecords = new ArrayList<>();

    @Override
    public void configure() throws Exception {

        from("kafka:cst?brokers=kafka-kafka-bootstrap:9092" +
                "&groupId=apps" +
                "&autoOffsetReset=earliest")
            .log("Message received from Kafka: ${body}")
            .process(exchange -> {
                kfkrecords.add(exchange.getMessage().getBody(String.class));
            });

        from("direct:getRecords")
            .process(exchange -> {
                ObjectMapper mapper = new ObjectMapper();

                String reply = mapper.writeValueAsString(kfkrecords);
                exchange.getMessage().setBody(reply);
                exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
            });
    
        from("direct:createRecord")
            .log("Just in case... Body:${body} , Header: ${header.record}")
            .setBody(simple("${body}"))
            .to("kafka:cst?brokers=kafka-kafka-bootstrap:9092");
    }

}