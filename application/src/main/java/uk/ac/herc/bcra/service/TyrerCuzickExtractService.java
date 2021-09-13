package uk.ac.herc.bcra.service;

import org.apache.commons.io.IOUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import uk.ac.herc.bcra.service.util.TyrerCuzickPathUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.util.*;

import javax.persistence.EntityManager;

import static uk.ac.herc.bcra.service.util.TyrerCuzickPathUtil.getTyrerCuzickPath;

@Service
@Transactional
public class TyrerCuzickExtractService {
    private final Logger log = LoggerFactory.getLogger(TyrerCuzickExtractService.class);
    private static String SQL_COMMAND;
    private EntityManager em;

    public TyrerCuzickExtractService(EntityManager em) {
        this.em = em;
        initialisePathVariables();
    }

    private void initialisePathVariables() {
        try {
            FileManager.getInstance().registerDir(TyrerCuzickPathUtil.getTyrerCuzickPath()+"/extract");
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("tyrercuzick/risk_assessment_extract.sql");
            String s = IOUtils.toString(inputStream);
            SQL_COMMAND = s.replace("#{risk_assessment_extract_file}",TyrerCuzickPathUtil.getTyrerCuzickExtractFile());
        } catch(Exception ex) {
            // Will only fail when ran on non-windows/ios/linux system.
        }
    }

    public void runTyrerCuzickDataExtract() throws Exception {
        log.info("Running Tyrer Cuzick data extract");
        em.createNativeQuery(SQL_COMMAND).executeUpdate();
    }
}
