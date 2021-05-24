package org.sid.cinema;

import org.sid.cinema.entities.Film;
import org.sid.cinema.entities.Salle;
import org.sid.cinema.entities.Ticket;
import org.sid.cinema.services.ICinemaInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class JeeGestionCinemaApplication implements CommandLineRunner {

	@Autowired
	private ICinemaInitService iCinemaInitService;

	@Autowired
	private RepositoryRestConfiguration restConfiguration;

	public static void main(String[] args) {
		SpringApplication.run(JeeGestionCinemaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		restConfiguration.exposeIdsFor(Film.class, Salle.class, Ticket.class);
		iCinemaInitService.initVilles();
		iCinemaInitService.initCinemas();
		iCinemaInitService.initSalles();
		iCinemaInitService.initPlaces();
		iCinemaInitService.initSeances();
		iCinemaInitService.initCategories();
		iCinemaInitService.initFilms();
		iCinemaInitService.initProjections();
		iCinemaInitService.initTickets();




	}

}
