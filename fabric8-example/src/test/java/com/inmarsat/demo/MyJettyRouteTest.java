package com.inmarsat.demo;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by david.hammerton on 7/15/2016.
 */
public class MyJettyRouteTest extends CamelTestSupport {

    private String body = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
            "<contacts>\n" +
            "  <contact>\n" +
            "    <first-name>Dave</first-name>\n" +
            "    <second-name>Hammerton</second-name>\n" +
            "    <email>david.hammerton@inmarsat.com</email>\n" +
            "  </contact>\n" +
            "  <contact>\n" +
            "    <first-name>Kevin</first-name>\n" +
            "    <second-name>Crocker</second-name>\n" +
            "    <email>kevin.crocker@inmarsat.com</email>\n" +
            "  </contact>\n" +
            "  <contact>\n" +
            "    <first-name>Keith</first-name>\n" +
            "    <second-name>Ball</second-name>\n" +
            "    <email>keith.ball@inmarsat.com</email>\n" +
            "  </contact>\n" +
            "</contacts>";

    @Produce(uri="direct:start")
    private ProducerTemplate template;

    @EndpointInject(uri="mock:result")
    private MockEndpoint resultEndpoint;

    private String seperator = System.lineSeparator();

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new MyJettyRoute(template.getDefaultEndpoint(), resultEndpoint);
    }

    @Test
    public void splitterTest() throws Exception {
        List<String> expectedBodies = new ArrayList<>();

        expectedBodies.add("<contact>" + seperator +
                "    <first-name>Dave</first-name>" + seperator +
                "    <second-name>Hammerton</second-name>" + seperator +
                "    <email>david.hammerton@inmarsat.com</email>" + seperator +
                "  </contact>");
        expectedBodies.add("<contact>" + seperator +
                "    <first-name>Kevin</first-name>" + seperator +
                "    <second-name>Crocker</second-name>" + seperator +
                "    <email>kevin.crocker@inmarsat.com</email>" + seperator +
                "  </contact>");
        expectedBodies.add("<contact>" + seperator +
                "    <first-name>Keith</first-name>" + seperator +
                "    <second-name>Ball</second-name>" + seperator +
                "    <email>keith.ball@inmarsat.com</email>" + seperator +
                "  </contact>");

        template.sendBody(body);

        resultEndpoint.expectedBodiesReceived(expectedBodies);

        resultEndpoint.assertIsSatisfied();
    }
}