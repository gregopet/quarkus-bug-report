package org.example;

import io.quarkiverse.wiremock.devservice.WireMockConfigKey;
import io.quarkus.virtual.threads.VirtualThreads;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.concurrent.ExecutorService;

@ApplicationScoped
public class MyService {

    private static final Logger LOG = Logger.getLogger(MyService.class);

    MyRemoteService myRemoteService;


    @Inject
    @VirtualThreads
    ExecutorService vThreads;

    @ConfigProperty(name = WireMockConfigKey.PORT)
    Integer wiremockPort; // the port WireMock server is listening on


    @Inject
    public MyService(
        @ConfigProperty(name = WireMockConfigKey.PORT) Integer wiremockPort
    ) {
        myRemoteService = RestClientBuilder.newBuilder()
                .baseUri("http://localhost:" + wiremockPort)
                .build(MyRemoteService.class);
    }

    void doOuterCall() {
        vThreads.submit(() -> {
            try {
                myRemoteService.demo();
            } catch (Exception e) {
                LOG.error("Error calling external service", e);
            }
        });
    }
}
