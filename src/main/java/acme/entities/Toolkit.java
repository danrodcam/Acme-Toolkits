package acme.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Toolkit extends AbstractEntity{

	protected static final long serialVersionUID = 1L;

	@Column(unique=true)
	@Pattern(regexp = "^[A-Z]{3}-[0-9]{3}(-[A-Z])?$")
	protected String code;
	
	@NotBlank
	@Size(max=100)
	protected String title;
	
	@NotBlank
	@Size(max=255)
	protected String description;
	
	@NotBlank
	@Size(max=255)
	protected String assembly_notes;
	
	@URL
	protected String link;
	
	//@OneToMany(cascade = CascadeType.ALL, mappedBy = "player", fetch = FetchType.EAGER)
	//public List<Tool> tool;
	
}
