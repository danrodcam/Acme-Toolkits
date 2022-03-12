package acme.entities;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import acme.framework.entities.AbstractEntity;

public class Chirp extends AbstractEntity{

	protected static final long serialVersionUID = 1L;
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	protected LocalDateTime creationMoment;
	
	@NotBlank
	@Size(max=100)
	protected String title;
	
	@NotBlank
	@Size(max=100)
	protected String author;
	
	@NotBlank
	@Size(max=255)
	protected String body;
	
	@Email
	protected String email;
}
