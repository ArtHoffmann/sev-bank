package code.sev.rest;

import code.sev.service.KreditkartenService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Path("kreditkarte")
public class KreditkartenRessource {

    private KreditkartenService kreditkartenService;

    public KreditkartenRessource(KreditkartenService kreditkartenService) {
        this.kreditkartenService = kreditkartenService;
    }

    @GET
    public Response findAllKK() {
        return Response.status(Response.Status.OK).entity(kreditkartenService.findAll()).build();
    }

    @GET
    @Path("{id}")
    public Response findNutzerById(@PathParam("id") Long id) throws Exception {
        return Response.status(Response.Status.OK).entity(kreditkartenService.findKreditkarteById(id)).build();
    }

    @GET
    @Path("withdraw/{id}/{amount}")
    public Response withdrawKK(@PathParam("id") Long id, @PathParam("amount") BigDecimal amount) throws Exception {
        return Response.status(Response.Status.OK).entity(kreditkartenService.withdrawMoney(id, amount)).build();
    }


    @POST
    @Path("{id}")
    public Response addKK(@PathParam("id") Long id) {
        return Response.status(Response.Status.CREATED).entity(kreditkartenService.addKreditkarteToGirokonto(id)).build();
    }

    @PUT
    @Path("{id}/{limit}")
    public Response editKK(@PathParam("id") Long id, @PathParam("limit") Long limit) {
        return Response.status(Response.Status.CREATED).entity(kreditkartenService.editKK(id, limit)).build();
    }

}
