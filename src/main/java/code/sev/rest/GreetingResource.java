package code.sev.rest;

import code.sev.service.HelloWorldService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/helloworld")
public class GreetingResource {

    @Inject
    private HelloWorldService helloWorld;

    @GET
    public Response helloWorld() {
        return Response.status(Response.Status.OK).entity(helloWorld.helloWorld()).build();
    }

}