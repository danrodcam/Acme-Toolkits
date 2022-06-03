package acme.forms;

import java.io.Serializable;
import java.util.Map;

import acme.entities.patronages.PatronageStatus;
import acme.framework.components.database.Record;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dashboard implements Serializable {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	
	Integer						numberOfComponents; 
	Map<Record,Map<String,Double>>			statsComponents;  //Componentes agrupados por currency y technology
	
	
	Integer						numberOfTools; 
	Map<String,Map<String,Double>>			statsTools;  //Tools agrupadas por currency

	
	 
	Map<PatronageStatus,Map<String,Double>>   statsPatronages;  //Patronages agrupados por el estado del Patronage
	
	
	Map<Record,Map<String,Double>>   statsPatronagesCurrency;  //Patronages agrupados por el estado del Patronage y por currency

	Double                          ratioComponentsChimpum;
	
	Map<String,Map<String,Double>>   statsChimpums;
	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
