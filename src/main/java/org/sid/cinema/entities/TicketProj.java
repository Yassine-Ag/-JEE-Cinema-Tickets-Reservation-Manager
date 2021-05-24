package org.sid.cinema.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "p2", types = Ticket.class)
public interface TicketProj {
    Long getId();
    String getNomClient();
    double getPrix();
    Integer getCodePayment();
    boolean isReserve();
    Place getPlace();
}
