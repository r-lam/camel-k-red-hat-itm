package db2db;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;

public class ElasticPopulator extends RouteBuilder {
    
    @Override
    public void configure() throws Exception {

        String SCQL = "SELECT * FROM a1.sensors";
        String ICQL = "INSERT INTO a1.sensors (id, value)";
        
        from("timer:refresh?period={{consumer.fetch.period}}")
            .log("Elastic Populator route Started!")
            .setHeader(Exchange.HTTP_METHOD).constant("GET")
            .to("https://api.fda.gov/drug/event.json?search=receivedate:{{api.dates}}&count=patient.reaction.reactionmeddrapt.exact")
            .unmarshal().json(JsonLibrary.Jackson, GeneralData.class)
            .convertBodyTo(String.class)
            .to("cql://{{cassandra.brokers.address}}?cql=" + ICQL);
        
        from("cql://{{cassandra.brokers.address}}?cql=" + SCQL)
            .log("Connected to Cassandra! Elastic route Started!")
            .marshal().json()
            .to("elasticsearch-rest://drugsSearch?operation=Index&indexName=drugs");
    }
}