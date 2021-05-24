package org.sid.cinema.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
@Entity @Data @AllArgsConstructor @NoArgsConstructor
@ToString

public class Seance {
	
   @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
   private Long id;
   
   @Temporal(TemporalType.TIME)
   private Date heureDebut;

   @OneToMany(mappedBy="seance")
   private Collection<Projection> projections;
    
}
