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
	
	Integer						numberOfComponents; 
	Map<Record,Double>			averageComponents;  //Componentes agrupados por currency y technology
	Map<Record,Double>			desviationComponents;
	Map<Record,Double>			maxComponents;
	Map<Record,Double>			minComponents;
	
	
	Integer						numberOfTools; 
	Map<String,Double>			averageTools;  //Tools agrupadas por currency
	Map<String,Double>			desviationTools;
	Map<String,Double>			maxTools;
	Map<String,Double>			minTools;
	
	
	Map<PatronageStatus,Integer>  numberOfPatronages; 
	Map<PatronageStatus,Double>   averagePatronages;  //Patronages agrupados por el estado del Patronage
	Map<PatronageStatus,Double>   desviationPatronages;
	Map<PatronageStatus,Double>   maxPatronages;
	Map<PatronageStatus,Double>   minPatronages;
	
	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
