package acme.systemConfiguration;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Range;

import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SystemConfiguration extends AbstractEntity{
	
	//Serialisation Identifier
	
	protected static final long	serialVersionUID	= 1L;
	
	@NotBlank
	protected String systemCurrency;
	
	@NotEmpty
	protected ArrayList<String> acceptedCurrency;
	
	protected ArrayList<String> strongSpamTerms;
	
	protected ArrayList<String> weakSpamTerms;
	
	@Range(min=0, max=1)
	protected Double strongSpamThreshold;
	
	@Range(min=0, max=1)
	protected Double weakSpamThreshold;
}
