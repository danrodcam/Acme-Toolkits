package acme.forms;

import java.io.Serializable;
import java.util.Map;

import acme.entities.patronages.PatronageStatus;
import acme.framework.components.database.Record;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDashboard implements Serializable {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	
	
	

	
	final Integer						numberOfComponents = null; 
	final Map<Record,Double>			averageComponents = null;  //Componentes agrupados por currency y technology
	final Map<Record,Double>			desviationComponents = null;
	final Map<Record,Double>			maxComponents = null;
	final Map<Record,Double>			minComponents = null;
	
	
	final Integer						numberOfTools = null; 
	final Map<String,Double>			averageTools = null;  //Tools agrupadas por currency
	final Map<String,Double>			desviationTools = null;
	final Map<String,Double>			maxTools = null;
	final Map<String,Double>			minTools = null;
	
	
	final Map<PatronageStatus,Integer>  numberOfPatronages = null; 
	final Map<PatronageStatus,Double>   averagePatronages = null;  //Patronages agrupados por el estado del Patronage
	final Map<PatronageStatus,Double>   desviationPatronages = null;
	final Map<PatronageStatus,Double>   maxPatronages = null;
	final Map<PatronageStatus,Double>   minPatronages = null;
	
	

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
