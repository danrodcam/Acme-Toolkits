package acme.forms;

import java.io.Serializable;
import java.util.Map;

import acme.framework.components.database.Record;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDashboard implements Serializable {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	
	
	

	
	final Integer						numberOfComponents; 
	final Map<Record,Double>			averageComponents;  //Componentes agrupados por currency y technology
	final Map<Record,Double>			desviationComponents;
	final Map<Record,Double>			maxComponents;
	final Map<Record,Double>			minComponents;
	
	
	final Integer						numberOfTools; 
	final Map<String,Double>			averageTools;  //Tools agrupadas por currency
	final Map<String,Double>			desviationTools;
	final Map<String,Double>			maxTools;
	final Map<String,Double>			minTools;
	
	
	final Map<PatronageStatus,Integer>  numberOfPatronages; 
	final Map<PatronageStatus,Double>   averagePatronages;  //Patronages agrupados por el estado del Patronage
	final Map<PatronageStatus,Double>   desviationPatronages;
	final Map<PatronageStatus,Double>   maxPatronages;
	final Map<PatronageStatus,Double>   minPatronages;
	
	

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
