package acme.entities.patronageReport;

import java.sql.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PatronageReport extends AbstractEntity {
	
	protected static final long serialVersionUID = 1L;
	
	@Past
	protected Date creationMoment;
	
	protected Integer serialNumber;
	
	protected String patronageCode;
	
	@NotBlank
	@Length(max=256)
	protected String memorandum;
	
	@URL
	protected String link;
	
	
	public String automaticSecuenceNumber() {
		int numSer;
		String codePatronage;
		String result;
		numSer = this.serialNumber;
		codePatronage = this.patronageCode;
		result = numSer + " : " + codePatronage;
		return result;
	}

}
