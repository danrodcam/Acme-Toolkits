package acme.entities.patronageReport;

import java.sql.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PatronageReport extends AbstractEntity {
	
	// Serialisation identifier -----------------------------------------------

	protected static final long serialVersionUID = 1L;
	
	// Attributes -------------------------------------------------------------

	@Past
	protected Date 					creationMoment;
	
	@NotBlank
	@Pattern(regexp = "^[0-9]{3}$")
	protected Integer 				serialNumber;
	
	@NotBlank
	@Pattern(regexp = "^[A-Z]{3}-[0-9]{3}(-[A-Z])?$")
	protected String 				patronageCode;
	
	@NotBlank
	@Length(max=256)
	protected String 				memorandum;
	
	@URL
	protected String 				link;
	
	
	public String automaticSecuenceNumber() {
		int numSer;
		String codePatronage;
		String result;
		numSer = this.serialNumber;
		codePatronage = this.patronageCode;
		result = numSer + " : " + codePatronage;
		return result;
	}
	
	// Derived attributes -----------------------------------------------------
	
	// Relationships ----------------------------------------------------------

}
