package uk.ac.herc.bcra.config;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.DependsOn;

@Component
@DependsOn("liquibase")
public class ApplicationStartup {
		
		@PostConstruct
		private void setupApplication(){

        }
		
}