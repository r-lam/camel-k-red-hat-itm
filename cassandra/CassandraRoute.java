// java

import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

public class CassandraRoute extends RouteBuilder {

    public ArrayList<String> c_result = new ArrayList<>();
    public Circuit circuit = new Circuit();

    String SCQL = "select * from a1.sensors";
    String INCQL = "insert into a1.sensors (id, sensor_name, month_year, value) values (?, ?, ?)";

    
    
    @Override
    public void configure() throws Exception {
        restConfiguration().component("jetty-http").bindingMode(RestBindingMode.auto);

        rest("/sensors")
            .get().type(Circuit[].class)
                .to("direct:read");
        
        rest("/")
            .post("new").type(Circuit[].class).outType(Circuit[].class)
                .to("direct:write");
            
        from("direct:read")
            .to("cql://{{cassandra.brokers.address}}?cql=" + SCQL);

        from("direct:write")
            .to("cql://{{cassandra.brokers.address}}?cql=" + INCQL);
        
    }
}