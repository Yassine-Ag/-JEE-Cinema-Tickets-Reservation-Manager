package org.sid.cinema.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
@ToString


public class Ticket {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String nomClient;
	private double prix;

	@Column(unique = false, nullable = true)
	private Integer codePayment;

	private boolean reserve;
	
	@ManyToOne
	@JsonBackReference
	private Place place;
	
	@ManyToOne
	@JsonBackReference
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Projection projection;

}
