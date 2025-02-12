package fr.pantheonsorbonne.miage.resources;

import fr.pantheonsorbonne.miage.dao.QuotaDAO;
import fr.pantheonsorbonne.miage.dto.BookingRequest;
import fr.pantheonsorbonne.miage.dto.Quota;
import fr.pantheonsorbonne.miage.service.InsufficientQuotaException;
import fr.pantheonsorbonne.miage.service.NoSuchQuotaException;
import fr.pantheonsorbonne.miage.service.QuotaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/vendor/{vendorId}/quota")
public class QuotaResource {

    @Inject
    QuotaService quotaService;

    @Inject
    QuotaDAO dao;

    @GET
    @Path("/{concertId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Quota getQuotaForVendorAndConcert(
            @PathParam("vendorId") int vendorId,
            @PathParam("concertId") int concertId) {
        return dao.getQuota(vendorId, concertId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Quota> getQuotaVendor(
            @PathParam("vendorId") int vendorId) {
        return dao.getQuotas(vendorId);
    }

    @PUT
    @Path("/{concertId}")

    public void bookConcert(
            @PathParam("vendorId") int vendorId,
            @PathParam("concertId") int concertId,
            BookingRequest bookingRequest) throws NoSuchQuotaException, InsufficientQuotaException {
        quotaService.bookTickets(
                vendorId,
                concertId,
                bookingRequest.seated(),
                bookingRequest.standing());

    }


}
