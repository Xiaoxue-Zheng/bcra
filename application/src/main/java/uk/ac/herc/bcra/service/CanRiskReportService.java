package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.domain.CanRiskReport;
import uk.ac.herc.bcra.domain.User;
import uk.ac.herc.bcra.service.dto.CanRiskReportDTO;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * Service Interface for managing {@link CanRiskReport}.
 */
public interface CanRiskReportService {

    void createCanRiskReportFromUserAndFile(User user, MultipartFile file) throws Exception;

    List<CanRiskReport> findAll();
    List<CanRiskReportDTO> findAllAsDto(int page, int size) throws Exception ;

    CanRiskReportDTO findOneAsDto(Long id) throws Exception;

    void delete(Long id);
}
