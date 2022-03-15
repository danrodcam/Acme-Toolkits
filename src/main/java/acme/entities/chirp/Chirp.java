package acme.entities.chirp;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Chirp extends AbstractEntity{

	// Serialisation identifier -----------------------------------------------

	protected static final long serialVersionUID = 1L;
	
	// Attributes -------------------------------------------------------------

	@DateTimeFormat(pattern="dd/MM/yyyy")
	protected LocalDateTime 		creationMoment;
	
	@NotBlank
	@Size(max=100)
	protected String 				title;
	
	@NotBlank
	@Size(max=100)
	protected String 				author;
	
	@NotBlank
	@Size(max=255)
	protected String 				body;
	
	@Email
	protected String 				email;
	
	// Derived attributes -----------------------------------------------------
	
	// Relationships ----------------------------------------------------------
		
}
