package acme.entities.item;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import acme.entities.toolkit.Toolkit;
import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Amount extends AbstractEntity {
	
	// Serialisation identifier -----------------------------------------------

	protected static final long serialVersionUID = 1L;
	
	// Attributes -------------------------------------------------------------

	
	protected Integer				units;
	
	
	// Derived attributes -----------------------------------------------------
	
	// Relationships ----------------------------------------------------------
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Toolkit 				toolkit;
	
	@Valid
	@ManyToOne(optional = false)
	protected Item					item;

}
