package org.example;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

import java.util.List;
import java.util.Set;


@RegisterRestClient
public interface MyRemoteService {

    @GET
    @Path("/endpoint")
    String demo();
}
