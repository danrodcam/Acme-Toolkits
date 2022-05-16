package acme.forms;

import java.io.Serializable;
import java.util.Collection;

import acme.entities.toolkit.Toolkit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemToolkitSearch implements Serializable {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	
	Collection<Toolkit>		toolkits;  
	String 				search;
	


	
	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
