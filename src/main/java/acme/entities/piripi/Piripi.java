package acme.entities.piripi;

import java.util.Date;

import javax.persistence.Entity;
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
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Piripi extends AbstractEntity {
	
	// Serialisation identifier -----------------------------------------------

		protected static final long		serialVersionUID	= 1L;

		// Attributes -------------------------------------------------------------
		
		
		@Pattern(regexp = "^[0-9]{2}-[0-9]{2}-[0-9]{2}$")
		protected String 				code;
		
		@Temporal(TemporalType.TIMESTAMP)
		@Past
		@NotNull
		protected Date					creationMoment;

		@NotBlank
		@Length(max=100)
		protected String				title;



		@NotBlank
		@Length(max=255)
		protected String				description;
		
		@NotNull
		protected Date                  initialDate;
		
		@NotNull
		protected Date                  finalDate;
		
		
		@NotNull
		@Valid
		protected Money					budget;

		@URL
		protected String				link;

		// Derived attributes -----------------------------------------------------
		
		@NotNull
		@Transient
		public Integer periodOfTime() {
			return (int) (this.finalDate.getTime() - this.initialDate.getTime()); 
		}

		// Relationships ----------------------------------------------------------
		
		


}