package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.CanRiskReportService;
import uk.ac.herc.bcra.service.dto.CanRiskReportDTO;
import uk.ac.herc.bcra.service.util.CanRiskUtil;
import uk.ac.herc.bcra.service.util.FileSystemUtil;
import uk.ac.herc.bcra.domain.CanRiskReport;
import uk.ac.herc.bcra.domain.StudyId;
import uk.ac.herc.bcra.domain.User;
import uk.ac.herc.bcra.repository.CanRiskReportRepository;
import uk.ac.herc.bcra.repository.StudyIdRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link CanRiskReport}.
 */
@Service
@Transactional
public class CanRiskReportServiceImpl implements CanRiskReportService {

    public static final int MAX_CAN_RISK_REPORT_SIZE = 10 * 1024 * 1024;

    private final Logger log = LoggerFactory.getLogger(CanRiskReportServiceImpl.class);

    private final CanRiskReportRepository canRiskReportRepository;

    private final StudyIdRepository studyIdRepository;

    public CanRiskReportServiceImpl(
        CanRiskReportRepository canRiskReportRepository, 
        StudyIdRepository studyIdRepository) {
        this.canRiskReportRepository = canRiskReportRepository;
        this.studyIdRepository = studyIdRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createCanRiskReportFromUserAndFile(User user, MultipartFile file) throws Exception {
        throwExceptionIfFileSizeExceeds10MB(file);

        CanRiskReport report = new CanRiskReport();
        report.setFilename(file.getOriginalFilename());
        report.setUploadedBy(user);

        String studyCode = getStudyCodeFromFile(file);
        Optional<StudyId> studyIdOptional = studyIdRepository.findOneByCode(studyCode);
        if (studyIdOptional.isPresent()) {
            report.setAssociatedStudyId(studyIdOptional.get());
        } else {
            throw new Exception("Study ID {" + studyCode + "} in CanRisk report does not match any valid Study ID");
        }

        try {
            canRiskReportRepository.save(report);
            saveFileData(file);
        } catch(Exception ex) {
            log.error(ex.getMessage());
            throw new Exception("Failed to save CanRisk report with Study Id {" + studyCode + "}");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CanRiskReport> findAll() {
        return canRiskReportRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CanRiskReportDTO> findAllAsDto(int page, int size) throws Exception {
        Page<CanRiskReport> reports = canRiskReportRepository.findAll(PageRequest.of(page, size));
        List<CanRiskReportDTO> reportDtos = new ArrayList<CanRiskReportDTO>();
        for (CanRiskReport report : reports) {
            CanRiskReportDTO dto = buildCanRiskReportDTO(report);
            // Deliberately not getting the file data here, as this can cause huge overhead.
            // File data is only accessed within the findOneAsDto functionality.

            reportDtos.add(dto);
        }

        return reportDtos;
    }


    @Override
    @Transactional(readOnly = true)
    public CanRiskReportDTO findOneAsDto(Long id) throws Exception {
        Optional<CanRiskReport> canRiskReportOptional = canRiskReportRepository.findById(id);
        if (canRiskReportOptional.isPresent()) {
            CanRiskReport report = canRiskReportOptional.get();
            return buildCanRiskReportDTOWithFileData(report);
        }

        return null;
    }

    public Optional<CanRiskReportDTO> findOneByAssociatedStudyId(StudyId studyIdId) {
        Optional<CanRiskReport> canRiskReportOptional = canRiskReportRepository.findOneByAssociatedStudyId(studyIdId);
        if (canRiskReportOptional.isPresent()) {
            CanRiskReport report = canRiskReportOptional.get();
            try {
                CanRiskReportDTO dto = buildCanRiskReportDTO(report);
                return Optional.of(dto);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        canRiskReportRepository.deleteById(id);
    }

    @Override
    public CanRiskReportDTO getCanRiskReportForParticipantLogin(String participantLogin) throws Exception {
        StudyId studyId = studyIdRepository.findOneByCode(participantLogin).get();
        CanRiskReport report = canRiskReportRepository.findOneByAssociatedStudyId(studyId).get();

        CanRiskReportDTO reportDto = new CanRiskReportDTO();
        reportDto.setFileData(getFileData(report));
        reportDto.setFilename(report.getFilename());
        reportDto.setStudyId(report.getAssociatedStudyId().getCode());
        reportDto.setUploadedBy(report.getUploadedBy().getLogin());

        return reportDto;
    }

    @Override
    public boolean hasCanRiskReportBeenUploadedForPatient(String participantLogin) throws Exception {
        StudyId studyId = studyIdRepository.findOneByCode(participantLogin).get();
        Optional<CanRiskReport> report = canRiskReportRepository.findOneByAssociatedStudyId(studyId);
        return report.isPresent();
    }

    private String getStudyCodeFromFile(MultipartFile file) {
        String filename = file.getOriginalFilename();
        return filename.replace(".pdf", "");
    }

    private void saveFileData(MultipartFile file) throws Exception {
        String canRiskReportDirectory = CanRiskUtil.getCanRiskReportDirectory();
        String fullFilePath = canRiskReportDirectory + '/' + file.getOriginalFilename();
        FileSystemUtil.writeBytesToFile(file.getBytes(), fullFilePath);
    }

    private byte[] getFileData(CanRiskReport report) throws Exception {
        String canRiskReportDirectory = CanRiskUtil.getCanRiskReportDirectory();
        String fullFilePath = canRiskReportDirectory + report.getFilename();
        return FileSystemUtil.readBytesFromFile(fullFilePath);
    }

    private void throwExceptionIfFileSizeExceeds10MB(MultipartFile file) throws Exception {
        if (file.getSize() > MAX_CAN_RISK_REPORT_SIZE) {
            throw new Exception("CanRisk report uploaded exceeds maximum file size.");
        }
    }

    private CanRiskReportDTO buildCanRiskReportDTO(CanRiskReport report) {
        CanRiskReportDTO dto = new CanRiskReportDTO();
        dto.setId(report.getId());
        dto.setFilename(report.getFilename());
        dto.setStudyId(report.getAssociatedStudyId().getCode());
        dto.setUploadedBy(report.getUploadedBy().getLogin());
        return dto;
    }

    private CanRiskReportDTO buildCanRiskReportDTOWithFileData(CanRiskReport report) throws Exception {
        CanRiskReportDTO dto = new CanRiskReportDTO();
        dto.setFileData(getFileData(report));
        dto.setFilename(report.getFilename());
        dto.setStudyId(report.getAssociatedStudyId().getCode());
        dto.setUploadedBy(report.getUploadedBy().getLogin());
        return dto;
    }
}
