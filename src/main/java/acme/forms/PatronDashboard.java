package acme.forms;

import java.io.Serializable;
import java.util.Map;

import acme.framework.components.database.Record;
import acme.framework.datatypes.Money;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatronDashboard implements Serializable {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	
	Map<Record,Integer>  numberOfPatronages; 
	Map<Record,Money>   averagePatronages;  //Patronages agrupados por el estado del Patronage y por currency
	Map<Record,Money>   desviationPatronages;
	Map<Record,Money>   maxPatronages;
	Map<Record,Money>   minPatronages;
	
	

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
