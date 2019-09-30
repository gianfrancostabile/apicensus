package com.accenture.java.apicensus.controller;

import com.accenture.java.apicensus.entity.Country;
import com.accenture.java.apicensus.entity.Person;
import com.accenture.java.apicensus.entity.RequestDTO;
import com.accenture.java.apicensus.entity.ResponseList;
import com.accenture.java.apicensus.processor.GetOnePersonProcessor;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

@Controller
public class GetResource extends MainResource {

    private ResponseList responseList = new ResponseList();

    @Autowired
    private GetOnePersonProcessor getOnePersonProcessor;

    @Override
    public void configure() throws Exception {
        super.configure();

        rest()
            .get("/{country}/{ssn}")
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .param()
                    .name("country").dataType("country").required(true)
                .endParam()
                .param()
                    .name("ssn").dataType("int").required(true)
                .endParam()
                .route()
                    .setBody(exchange -> {
                        Integer ssn = exchange.getIn().getHeader("ssn", Integer.class);
                        Country country = exchange.getIn().getHeader("country", Country.class);

                        return new RequestDTO(ssn, country);
                    })
                    .process(getOnePersonProcessor)
            .endRest()
            .get("/{country}")
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .type(Integer[].class)
                .param()
                    .name("country").dataType("country").required(true)
                .endParam()
                .route()
                    .process(exchange -> responseList = new ResponseList())
                    .to("direct:doRequestSplit")
                    .process(exchange -> {
                        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, responseList.getStatusCode());
                        exchange.getOut().setBody(responseList);
                    })
            .endRest();


        from("direct:doRequestSplit")
            .split(body())
                .setBody(exchange -> {
                    Integer ssn = exchange.getIn().getBody(Integer.class);
                    Country country = exchange.getIn().getHeader("country", Country.class);

                    return new RequestDTO(ssn, country);
                })
                .process(getOnePersonProcessor)
                .choice()
                    .when(simple("${header.CamelHttpResponseCode} == 200"))
                        .process(exchange -> responseList.addSuccess(exchange.getIn().getBody(Person.class)))
                    .otherwise()
                        .process(exchange -> responseList.addError(exchange.getIn().getBody(Integer.class)))
                .endChoice()
            .end();
    }
}
