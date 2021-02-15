package uk.ac.herc.bcra.config;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.DependsOn;
import org.springframework.beans.factory.annotation.Autowired;

import uk.ac.herc.bcra.service.DataGenerationService;
import uk.ac.herc.bcra.service.StudyIdService;

@Component
@DependsOn("liquibase")
public class ApplicationStartup {

		@Autowired
		private StudyIdService studyIdService;

		@Autowired
		private DataGenerationService dataGenerationService;
		
		@PostConstruct
		private void setupApplication(){
			// Assume that if TST_1 studyId does not exist then the test data needs generated.
			if(!studyIdService.getStudyIdByCode("TST_1").isPresent()) {
				dataGenerationService.generateTestData();
			}
        }
		
}