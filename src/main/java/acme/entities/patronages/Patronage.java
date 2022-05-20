package acme.entities.patronages;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.datatypes.Money;
import acme.framework.entities.AbstractEntity;
import acme.roles.Inventor;
import acme.roles.Patron;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Patronage extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	
	@NotNull
	protected PatronageStatus		status;

	@NotBlank
	@Length(max=255)
	protected String				legalStuff;
	
	@Column(unique = true)
	@NotNull
	@Pattern(regexp = "^[A-Z]{3}-[0-9]{3}(-[A-Z])?$")
	protected String 				code;



	@Valid
	protected Money					budget;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	protected Date					creationMoment;
	
	@URL
	protected String				optionalLink;
	
	@NotNull
	protected Date  				initialDate;
	
	@NotNull
	protected Date  				finalDate;
	
	@NotNull
	protected boolean				isPublished;

	// Derived attributes -----------------------------------------------------
	
	@NotNull
	@Transient
	public Integer periodOfTime() {
		return (int) (this.finalDate.getTime() - this.initialDate.getTime()); 
		
		
		
	}
	
	// Relationships ----------------------------------------------------------
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Inventor inventor;
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Patron patron;
	
	

}

