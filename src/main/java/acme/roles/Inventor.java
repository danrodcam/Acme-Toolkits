package acme.roles;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.patronages.Patronage;
import acme.framework.roles.UserRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Inventor extends UserRole {
	
	// Serialisation identifier -----------------------------------------------
	
	protected static final long serialVersionUID = 1L;
	
	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 100)
	protected String 				company;
	
	@NotBlank
	@Length(max = 255)
	protected String 				statement;
	
	@URL
	protected String 				optionalLink;
	
	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
	

}
