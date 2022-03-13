package acme.entities.patronages;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.entities.AbstractEntity;
import ch.qos.logback.core.util.Duration;
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
	@NotNull
	@Length(max=255)
	protected String				legalStuff;
	
	@Column(unique = true)
	@NotNull
	@Pattern(regexp = "^[A-Z]{3}-[0-9]{3}(-[A-Z])?$")
	protected String 				code;



	@NotNull
	@Positive
	protected Double				budget;
	
	@URL
	protected String				optionalLink;
	
	@NotNull
	protected Date  				initialDate;
	
	@NotNull
	protected Date  				finalDate;

	// Derived attributes -----------------------------------------------------
	
	@Transient
	public Duration periodOfTime() {
		final long diff = this.finalDate.getTime() - this.initialDate.getTime(); 
		
		return Duration.buildByMilliseconds(diff);
		
		
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
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected PatronageReport patronageReport;
	

}

