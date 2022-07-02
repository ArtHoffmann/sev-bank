package code.sev.rest;

import code.sev.model.FestgeldkontoDO;
import code.sev.service.FestgeldKontoService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Path("/festgeldkonto")
public class FestgeldKontoRessource {

    private FestgeldKontoService festgeldKontoService;

    public FestgeldKontoRessource(FestgeldKontoService festgeldKontoService) {
        this.festgeldKontoService = festgeldKontoService;
    }

    @GET
    public Response findAll() {
        return Response.status(Response.Status.OK).entity(festgeldKontoService.findAll()).build();
    }

    @GET
    @Path("{id}")
    public Response findFestgeldKontoById(@PathParam("id") Long id)  {
        return Response.status(Response.Status.OK).entity(festgeldKontoService.findFestgeldkontoById(id)).build();
    }

    @DELETE
    @Path("{kundennummer}")
    public Response deleteFestgeldKontoById(@PathParam("kundennummer") Long kundennummer)  {
        festgeldKontoService.deleteFestgeldkontoById(kundennummer);
        return Response.status(Response.Status.OK).entity("DELETED").build();
    }

    @POST
    public Response addFestgeldKonto(FestgeldkontoDO festgeldkontoDO) {
        FestgeldkontoDO konto = festgeldKontoService.addFestgeldkonto(festgeldkontoDO);
        return Response.status(Response.Status.CREATED).entity(konto).build();
    }

    @PUT
    public Response editFestgeldKonto(FestgeldkontoDO festgeldkontoDO) {
        FestgeldkontoDO konto = festgeldKontoService.updateFestgeldkonto(festgeldkontoDO);
        return Response.status(Response.Status.CREATED).entity(konto).build();
    }

    @PUT
    @Path("/despolimit/{id}/{amount}/{pin}")
    public Response setDispolimit(@PathParam("id") Long id, @PathParam("amount") BigDecimal limit, @PathParam("pin") int pin) {
        return Response.status(Response.Status.CREATED).entity(festgeldKontoService.setDispoLimit(id, limit, pin)).build();
    }

    @PUT
    @Path("/pin/{id}/{newPin}/{oldPin}")
    public Response changePin(@PathParam("id") Long id, @PathParam("newPin") int newPin, @PathParam("oldPin") int oldPin) {
        return Response.status(Response.Status.CREATED).entity(festgeldKontoService.changePing(id, newPin, oldPin)).build();
    }

    @PUT
    @Path("withdraw/{id}/{amount}/{pin}")
    public Response withdrawMoneay(@PathParam("id") Long id, @PathParam("amount") BigDecimal amount, @PathParam("pin") int pin) throws Exception {
        return Response.status(Response.Status.CREATED).entity(festgeldKontoService.withdrawMoney(amount, id, pin)).build();
    }

    @PUT
    @Path("/deposit/{id}/{amount}/{pin}")
    public Response depositMoney(@PathParam("id") Long id, @PathParam("amount") BigDecimal amount, @PathParam("pin") int pin) {
        return Response.status(Response.Status.CREATED).entity(festgeldKontoService.depositMoney(amount, id, pin)).build();
    }

}
