package acme.features.any.chirp;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.chirp.Chirp;
import acme.features.systemConfiguration.SpamFilter.SystemConfigurationSpamFilterService;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractCreateService;

@Service
public class AnyChirpCreateService implements AbstractCreateService<Any, Chirp> {
	
	// Internal state
	
	@Autowired
	protected AnyChirpRepository repository;
	
	@Autowired
	protected SystemConfigurationSpamFilterService spamFilterService;
	
	// Interface
	
	@Override
	public boolean authorise(final Request<Chirp> request) {
		assert request != null;
		
		return true;
	}
	
	@Override
	public Chirp instantiate(final Request<Chirp> request) {
		assert request != null;
		
		Chirp result;
		
		Date moment;
		
		moment = new Date(System.currentTimeMillis() -1);
		
		result = new Chirp();
		result.setTitle("");
		result.setAuthor("");
		result.setBody("");
		result.setCreationMoment(moment);
		result.setEmail("");
		
		return result;
	}
	
	@Override
	public void bind(final Request<Chirp> request, final Chirp entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors, "title", "author", "body", "email");
	}
	
	@Override
	public void validate(final Request<Chirp> request, final Chirp entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		if (!errors.hasErrors("title")) {
			errors.state(request, !this.spamFilterService.isSpam(entity.getTitle()), "title", "any.chirp.form.error.spam");
		}
		if (!errors.hasErrors("author")) {
			errors.state(request, !this.spamFilterService.isSpam(entity.getAuthor()), "author", "any.chirp.form.error.spam");
		}
		if (!errors.hasErrors("body")) {
			errors.state(request, !this.spamFilterService.isSpam(entity.getBody()), "body", "any.chirp.form.error.spam");
		}
		
		errors.state(request, request.getModel().getBoolean("confirm"), "confirm", "any.chirp.form.error.must-confirm");
	}
	
	@Override
	public void unbind(final Request<Chirp> request, final Chirp entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "title", "author", "body", "email", "creationMoment");
		model.setAttribute("confirm", "false");
	}
	
	@Override
	public void create(final Request<Chirp> request, final Chirp entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.save(entity);
	}
}
