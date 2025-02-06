package org.example;

import io.quarkus.virtual.threads.VirtualThreads;
import jakarta.enterprise.context.ContextNotActiveException;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.concurrent.ExecutorService;
import io.quarkiverse.wiremock.devservice.WireMockConfigKey;

@Path("/hello")
public class ExampleResource {

    private static Logger LOG = Logger.getLogger(ExampleResource.class);

    @Inject
    MyService myService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        try {
            myService.doOuterCall();
            return "hey";
        } catch (Throwable t) {
            return "Error: " + t.getMessage();
        }
    }
}
