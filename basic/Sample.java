import org.apache.camel.Exchange;

public class Sample extends org.apache.camel.builder.RouteBuilder {
    @Override
    public void configure() throws Exception {
        rest()
            .get("/hello")
            .to("direct:hello");

        from("direct:hello")
            .setHeader(Exchange.CONTENT_TYPE, constant("text/plain"))
            .transform().simple("Hello World");
    }
}