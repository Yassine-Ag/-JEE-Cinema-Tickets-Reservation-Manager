package org.sid.cinema.services;

import org.sid.cinema.entities.*;
import org.sid.cinema.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Service
@Transactional
public class CinemaInitserviceImplementation implements ICinemaInitService {

    @Autowired
    private VilleRepository villeRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private SalleRepository salleRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private SeanceRepository seanceRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private ProjectionRepository projectionRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public void initVilles() {
        Stream.of("Casablanca", "Marrakech", "Rabat", "Tanger")
                .forEach(nomVille -> {
                    Ville ville = new Ville();
                    ville.setName(nomVille);
                    villeRepository.save(ville);
                });
    }

    @Override
    public void initCinemas() {
        villeRepository.findAll()
                .forEach(ville -> Stream.of("MegaRama", "IMAX", "FOUNOUN", "Chahrazad", "DAOULIZ")
                        .forEach(nomCinema -> {
                            Cinema cinema = new Cinema();
                            cinema.setName(nomCinema);
                            cinema.setVille(ville);
                            cinema.setNombreSalles(3 + (int) (Math.random() * 7));
                            cinemaRepository.save(cinema);
                        }));
    }

    @Override
    public void initSalles() {

        cinemaRepository.findAll()
                .forEach(cinema -> {
                    for (int i = 0; i < cinema.getNombreSalles(); i++) {
                        Salle salle = new Salle();
                        salle.setName("Salle" + (i + 1));
                        salle.setCinema(cinema);
                        salle.setNombrePlace(10 + ((int) (Math.random() * 5)));
                        salleRepository.save(salle);
                    }
                });

    }

    @Override
    public void initPlaces() {
        for (Salle salle : salleRepository.findAll()) {
            for (int i = 0; i < salle.getNombrePlace(); i++) {
                Place place = new Place();
                place.setSalle(salle);
                place.setNumero(i + 1);
                placeRepository.save(place);
            }
        }

    }

    @Override
    public void initSeances() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Stream.of("16:30", "18:15", "20:00", "22:30")
                .forEach(seanceDebut -> {
                    Seance seance = new Seance();
                    try {
                        seance.setHeureDebut(dateFormat.parse(seanceDebut));
                        seanceRepository.save(seance);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void initCategories() {
        Stream.of("Action", "Fantasie", "Sci-Fi", "Histoire", "Comedie", "Drama", "Documentation")
                .forEach(nomCategorie -> {
                    Categorie categorie = new Categorie();
                    categorie.setName(nomCategorie);
                    categorieRepository.save(categorie);
                });
    }

    @Override
    public void initFilms() {
        List<Categorie> categories = categorieRepository.findAll();
        AtomicInteger categorieIndex = new AtomicInteger();
        double[] durees = new double[]{2.00, 1.30, 1.00, 3.00, 2.30};
        Stream.of("CREED", "Avatar", "2001 - A Space Odyssey",
                "The Founder", "Neighbors", "Titanic",
                "Money Heist_The PHENOMENON")
                .forEach(titreFilm -> {
                    Film film = new Film();
                    film.setTitre(titreFilm);
                    film.setDuree(durees[new Random().nextInt(durees.length)]);
                    film.setPhoto(titreFilm.replaceAll("\\s+", ""));
                    film.setCategorie(categories.get((categorieIndex.get() % categories.size())));
                    getCategorieIndex(categories, categorieIndex);
                    filmRepository.save(film);
                });

    }


    @Override
    public void initProjections() {
        double[] prixFilms = new double[]{30, 40, 50, 60, 70, 80, 90};
        List<Film> films = filmRepository.findAll();
        for (Ville ville : villeRepository.findAll()) {
            ville.getCinemas().forEach(cinema -> {
                for (Salle salle : cinema.getSalles()) {
                    int index = new Random().nextInt(films.size());
                    Film film = films.get(index);
                    seanceRepository.findAll().forEach(seance -> {
                        Projection projection = new Projection();
                        projection.setDateProjection(new Date());
                        projection.setFilm(film);
                        projection.setPrix(prixFilms[new Random().nextInt(prixFilms.length)]);
                        projection.setSalle(salle);
                        projection.setSeance(seance);
                        projectionRepository.save(projection);
                    });
                }
            });
        }

    }

    @Override
    public void initTickets() {
        projectionRepository.findAll()
                .forEach(p -> p.getSalle().getPlaces().forEach(place -> {
                    Ticket ticket = new Ticket();
                    ticket.setPlace(place);
                    ticket.setPrix(p.getPrix());
                    ticket.setProjection(p);
                    ticket.setReserve(false);
                    ticketRepository.save(ticket);

                }));

    }

    // Fonctions qui ne sont en relations de l'initialisations des donnees!
    private void getCategorieIndex(List<Categorie> categories, AtomicInteger categorieIndex) {
        if ((categorieIndex.get() < categories.size())) {
            categorieIndex.getAndIncrement();
        } else {
            categorieIndex.set(0);
        }
    }

}
