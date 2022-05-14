package acme.features.systemConfiguration.SpamFilter;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;
import acme.systemConfiguration.SystemConfiguration;

@Repository
public interface SystemConfigurationSpamFilterRepository extends AbstractRepository{

	@Query("select SC from SystemConfiguration SC")
	Collection<SystemConfiguration> findAllConfigurations();
	
}
