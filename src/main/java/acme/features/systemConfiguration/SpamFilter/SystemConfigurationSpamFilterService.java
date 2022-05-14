package acme.features.systemConfiguration.SpamFilter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.systemConfiguration.SystemConfiguration;
import spam_filter.SpamFilter;

@Service
public class SystemConfigurationSpamFilterService {

	@Autowired
	protected SystemConfigurationSpamFilterRepository repository;
	
	private void initFilter() {
		
		final List<SystemConfiguration> Configurations = new ArrayList<>(this.repository.findAllConfigurations());
		final SystemConfiguration sysCon = Configurations.get(0);
		
		SpamFilter.initFilter(sysCon.getStrongSpamTerms(), sysCon.getWeakSpamTerms(), sysCon.getStrongSpamThreshold(), sysCon.getWeakSpamThreshold());
	}
	
	public boolean isSpam(final String cadena) {
		this.initFilter();
		return SpamFilter.isSpam(cadena);
	}
}