package uk.ac.herc.bcra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DataGenerationService {

    private final Logger log = LoggerFactory.getLogger(DataGenerationService.class);

    @Autowired
    private StudyIdService studyIdService;

	public void generateTestData() {
        // TODO: Tidy up as part of later tickets (CLIN-1140 and CLIN-1005)
		log.info("Adding test data...");

		try {
            studyIdService.createStudyIdFromCode("TST_1");

		} catch (Exception e) {
			log.error("Problem adding test data.", e);
		}

		log.info("Finished adding test data.");
	}

}

