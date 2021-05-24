package org.sid.cinema.controllers;

import lombok.Data;
import org.sid.cinema.entities.Film;
import org.sid.cinema.entities.Ticket;
import org.sid.cinema.repositories.FilmRepository;
import org.sid.cinema.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
public class CinemaRestController {
    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping(path = "/imageFilm/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable(name = "id")Long id) throws Exception{
      Film film = filmRepository.findById(id).get();
      String photoname = film.getPhoto();
      File file = new File(System
              .getProperty("user.home") + "/cinema/Posters/"+ photoname + ".jpg");
      return Files.readAllBytes(Paths.get(file.toURI()));
    }

    @PostMapping("/payerTickets")
    @Transactional
    @CrossOrigin("*")
    public List<Ticket> payerTicket(@RequestBody TicketForm ticketForm){
        List<Ticket> tickets = new ArrayList<>();
        ticketForm.getTickets().forEach(idTicket ->{
           Ticket ticket = ticketRepository.findById(idTicket).get();
           ticket.setNomClient(ticketForm.getNomClient());
           ticket.setReserve(true);
           ticketRepository.save(ticket);
           tickets.add(ticket);
        });
        return tickets;
    }

}

@Data
class  TicketForm{
    private String nomClient;
    private  List<Long> tickets = new ArrayList<>();
}
