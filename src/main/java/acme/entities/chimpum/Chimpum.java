package acme.entities.chimpum;

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
public class Chimpum extends AbstractEntity{
	
	// Serialisation identifier -----------------------------------------------

		protected static final long serialVersionUID = 1L;
		
		// Attributes -------------------------------------------------------------

		//@Pattern(regexp = "^(0?[1-9]|[12][0-9]|3[01])[\\/\\-](0?[1-9]|1[012])[\\/\\-]\\d{4}$")
		protected String 				code;
		
		@Temporal(TemporalType.TIMESTAMP)
		@Past
		@NotNull
		protected Date					creationMoment;
		
		@NotBlank
		@Length(max=100)
		protected String 				title;
		
		@NotBlank
		@Length(max=255)
		protected String 				description;
		
		@NotNull
		protected Date 					initialDate;
		
		@NotNull
		protected Date 					finalDate;
		
		@Valid
		protected Money					budget;
		
		@URL
		protected String				optionalLink;
		
		// Derived attributes -----------------------------------------------------
		
		@NotNull
		@Transient
		public Integer periodOfTime() {
			return (int) (this.finalDate.getTime() - this.initialDate.getTime()); 
		}
		
		// Relationships ----------------------------------------------------------
}
