package acme.entities.item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.datatypes.Money;
import acme.framework.entities.AbstractEntity;
import acme.roles.Inventor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Item extends AbstractEntity {
	
	// Serialisation identifier -----------------------------------------------

	protected static final long serialVersionUID = 1L;
	
	// Attributes -------------------------------------------------------------

	@NotBlank
	protected String 				name;
	
	@Pattern(regexp = "^[A-Z]{3}-[0-9]{3}(-[A-Z])?$")
	@Column(unique=true)
	protected String 				code;
	
	@NotBlank
	@Length (max = 100)
	protected String 				technology;
	
	@NotBlank
	@Length (max = 255)
	protected String 				description;
	
	@Valid
	protected Money 				retailPrice;
	
	@URL
	protected String 				link;
	
	@NotNull
	protected ItemType 				type;
	
	protected Boolean				published;
	
	// Derived attributes -----------------------------------------------------
	
	// Relationships ----------------------------------------------------------	
	
	@NotNull
	@Valid
	@ManyToOne(optional=false)
	protected Inventor 				inventor;
	
	

	
	
	
}
