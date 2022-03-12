package acme.entities.component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.tool.Tool;
import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Component extends AbstractEntity {

	protected static final long serialVersionUID = 1L;
	
	@NotBlank
	protected String name;
	
	@Pattern(regexp = "^[A-Z]{3}-[0-9]{3}(-[A-Z])?$")
	@Column(unique = true)
	protected String code;
	
	@NotBlank
	@Length(max = 100)
	protected String technology;
	
	@NotBlank
	@Length(max = 255)
	protected String description;
	
	@Positive
	protected Double retailPrice;
	
	@URL
	protected String optionalLink;
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Tool tool;
	
}
