package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.service.util.TyrerCuzickPathUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;

import javax.persistence.EntityManager;

@Service
@Transactional
public class TyrerCuzickExtractService {
    private final Logger log = LoggerFactory.getLogger(TyrerCuzickExtractService.class);
    private static String TYRER_CUZICK_EXTRACT_SQL_PATH;

    private EntityManager em;

    public TyrerCuzickExtractService(EntityManager em) {
        this.em = em;
        initialisePathVariables();
    }

    private void initialisePathVariables() {
        try {
            TYRER_CUZICK_EXTRACT_SQL_PATH = TyrerCuzickPathUtil.getTyrerCuzickExtractSql();
        } catch(Exception ex) {
            // Will only fail when ran on non-windows/ios/linux system.
        }
    }

    public void runTyrerCuzickDataExtract() throws Exception {
        log.info("Running Tyrer Cuzick data extract");

        File sqlExtractFile = new File(TYRER_CUZICK_EXTRACT_SQL_PATH);
        String sqlCommand = readFile(sqlExtractFile);
        em.createNativeQuery(sqlCommand).executeUpdate();
    }

    private String readFile(File file) throws Exception {
        Scanner fileReader = new Scanner(file);
        String fileData = "";

        while(fileReader.hasNextLine()) {
            fileData += fileReader.nextLine() + " ";
        }

        fileReader.close();

        return fileData;
    }
}