package code.sev.rest;

import code.sev.model.FestgeldkontoDO;
import code.sev.model.GirokontoDO;
import code.sev.service.GirokontoService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Path("/girokonto")
public class GirokontoRessource {

    private GirokontoService girokontoService;

    public GirokontoRessource(GirokontoService girokontoService) {
        this.girokontoService = girokontoService;
    }

    @GET
    public Response findAll() {
        return Response.status(Response.Status.OK).entity(girokontoService.findAll()).build();
    }

    @GET
    @Path("{id}")
    public Response findGiroKontoById(@PathParam("id") Long id) {
        return Response.status(Response.Status.OK).entity(girokontoService.findFestgeldkontoById(id)).build();
    }

    @DELETE
    @Path("{kundennummer}")
    public Response deleteGiroKontoById(@PathParam("kundennummer") Long kundennummer) {
        girokontoService.deleteFestgeldkontoById(kundennummer);
        return Response.status(Response.Status.OK).entity("DELETED").build();
    }

    @POST
    public Response addGiroKonto(GirokontoDO girokontoDO) {
        GirokontoDO gk = girokontoService.addGirokonto(girokontoDO);
        return Response.status(Response.Status.CREATED).entity(gk).build();
    }

    @PUT
    public Response editFestgeldKonto(GirokontoDO girokontoDO) {
        GirokontoDO gk = girokontoService.updateGirokonto(girokontoDO);
        return Response.status(Response.Status.CREATED).entity(gk).build();
    }

    @PUT
    @Path("/despolimit/{id}/{amount}/{pin}")
    public Response setDispolimit(@PathParam("id") Long id, @PathParam("amount") BigDecimal limit, @PathParam("pin") int pin) {
        return Response.status(Response.Status.CREATED).entity(girokontoService.setDispoLimit(id, limit, pin)).build();
    }

    @PUT
    @Path("/pin/{id}/{newPin}/{oldPin}")
    public Response changePin(@PathParam("id") Long id, @PathParam("newPin") int newPin, @PathParam("oldPin") int oldPin) {
        return Response.status(Response.Status.CREATED).entity(girokontoService.changePin(id, newPin, oldPin)).build();
    }

    @PUT
    @Path("withdraw/{id}/{amount}/{pin}")
    public Response withdrawMoneay(@PathParam("id") Long id, @PathParam("amount") BigDecimal amount, @PathParam("pin") int pin) throws Exception {
        return Response.status(Response.Status.CREATED).entity(girokontoService.withdrawMoney(amount, id, pin)).build();
    }

    @PUT
    @Path("/deposit/{id}/{amount}/{pin}")
    public Response depositMoney(@PathParam("id") Long id, @PathParam("amount") BigDecimal amount, @PathParam("pin") int pin) {
        return Response.status(Response.Status.CREATED).entity(girokontoService.depositMoney(amount, id, pin)).build();
    }

}
