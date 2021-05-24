package org.sid.cinema.entities;

import org.springframework.data.rest.core.config.Projection;

import java.util.Collection;
import java.util.Date;

@Projection(name="p1",types = {org.sid.cinema.entities.Projection.class})
public interface ProjectionProj {
    Long getId();
    double getPrix();
    Date getDateProjection();
    Salle getSalle();
    Film getFilm();
    Seance getSeance();
    Collection<Ticket> getTickets();
}
