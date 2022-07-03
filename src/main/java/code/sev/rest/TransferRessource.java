package code.sev.rest;

import code.sev.exception.TransferException;
import code.sev.service.TransferService;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Path("ueberweiseung")
public class TransferRessource {

    private TransferService transferService;

    public TransferRessource(TransferService transferService) {
        this.transferService = transferService;
    }

    @PUT
    @Path("/{vonkonto}/{aufkonto}/{amount}/{pin}")
    public Response depositMoney(@PathParam("vonkonto") Long vonkonto,
                                 @PathParam("aufkonto") Long aufkonto,
                                 @PathParam("amount") BigDecimal amount,
                                 @PathParam("pin") int pin) throws TransferException {

        return Response.status(Response.Status.OK)
                .entity(transferService.transferMoney(vonkonto, aufkonto, amount, pin))
                .build();
    }
}
