package org.sid.cinema.entities;

import java.util.Collection;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Entity @Data @AllArgsConstructor @NoArgsConstructor
@ToString

public class Categorie {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(length = 80)
	private String name;
	@OneToMany(mappedBy="categorie")
	@JsonProperty(access = Access.WRITE_ONLY)
	private Collection<Film> films;
	
	

}
