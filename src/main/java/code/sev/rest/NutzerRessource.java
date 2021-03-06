package code.sev.rest;

import code.sev.model.BankNutzerDO;
import code.sev.model.BankNutzerTO;
import code.sev.service.NutzerGirokontoService;
import code.sev.service.NutzerService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/nutzer")
public class NutzerRessource {

    private NutzerService nutzerService;
    private NutzerGirokontoService nutzerGirokontoService;

    public NutzerRessource(NutzerService nutzerService, NutzerGirokontoService nutzerGirokontoService) {
        this.nutzerService = nutzerService;
        this.nutzerGirokontoService = nutzerGirokontoService;
    }


    @GET
    public Response findAllNutzer(){
        return Response.status(Response.Status.OK).entity(nutzerService.findAllNutzer()).build();
    }

    @GET
    @Path("{id}")
    public Response findNutzerById(@PathParam("id") Long id) throws Exception {
        return Response.status(Response.Status.OK).entity(nutzerService.findNutzerById(id)).build();
    }


    @POST
    public Response addNutzer(BankNutzerDO nutzer) {
        nutzerService.addNutzer(nutzer);
        return Response.status(Response.Status.CREATED).entity(nutzer).build();
    }

    @PUT
    @Path("{kdnr}")
    public Response findNutzerbyKdnr(@PathParam("kdnr") Long kdnr, BankNutzerTO nutzerTO) {
        BankNutzerDO bankNutzer = nutzerService.editNutzer(kdnr, nutzerTO);
        return Response.status(Response.Status.ACCEPTED).entity(bankNutzer).build();
    }

    @POST
    @Path("{girokontoId}/{nutzerId}")
    public Response addUNutzerToGirokonto(@PathParam("girokontoId") Long girokontoId, @PathParam("nutzerId") Long nutzerId) throws Exception {
        return Response.status(Response.Status.CREATED).entity(nutzerGirokontoService.addNutzerGKVerbindung(girokontoId, nutzerId)).build();
    }
}
