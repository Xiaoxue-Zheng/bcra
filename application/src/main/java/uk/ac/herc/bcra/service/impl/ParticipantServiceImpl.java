package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.domain.enumeration.ParticipantContactWay;
import uk.ac.herc.bcra.repository.AuthorityRepository;
import uk.ac.herc.bcra.security.RoleManager;
import uk.ac.herc.bcra.service.IdentifiableDataService;
import uk.ac.herc.bcra.service.ParticipantService;
import uk.ac.herc.bcra.service.AnswerResponseService;
import uk.ac.herc.bcra.service.StudyIdService;
import uk.ac.herc.bcra.service.ProcedureService;
import uk.ac.herc.bcra.domain.enumeration.ResponseState;
import uk.ac.herc.bcra.repository.IdentifiableDataRepository;
import uk.ac.herc.bcra.repository.ParticipantRepository;
import uk.ac.herc.bcra.repository.UserRepository;
import uk.ac.herc.bcra.service.dto.ParticipantActivationDTO;
import uk.ac.herc.bcra.service.dto.ParticipantDTO;
import uk.ac.herc.bcra.service.dto.ParticipantDetailsDTO;
import uk.ac.herc.bcra.service.dto.ParticipantExistsDTO;
import uk.ac.herc.bcra.service.mapper.ParticipantMapper;
import uk.ac.herc.bcra.web.rest.errors.EmailAlreadyUsedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing {@link Participant}.
 */
@Service
@Transactional
public class ParticipantServiceImpl implements ParticipantService {

    private final Logger log = LoggerFactory.getLogger(ParticipantServiceImpl.class);

    private final ParticipantRepository participantRepository;

    private final ParticipantMapper participantMapper;

    private final IdentifiableDataService identifiableDataService;

    private final IdentifiableDataRepository identifiableDataRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AnswerResponseService answerResponseService;

    private final StudyIdService studyIdService;

    private final ProcedureService procedureService;

    private final AuthorityRepository authorityRepository;

    public ParticipantServiceImpl(
        ParticipantRepository participantRepository,
        ParticipantMapper participantMapper,
        IdentifiableDataService identifiableDataService,
        IdentifiableDataRepository identifiableDataRepository,
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        AnswerResponseService answerResponseService,
        StudyIdService studyIdService,
        ProcedureService procedureService,
        AuthorityRepository authorityRepository
    ) {
        this.participantRepository = participantRepository;
        this.participantMapper = participantMapper;
        this.identifiableDataService = identifiableDataService;
        this.identifiableDataRepository = identifiableDataRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.answerResponseService = answerResponseService;
        this.studyIdService = studyIdService;
        this.procedureService = procedureService;
        this.authorityRepository = authorityRepository;
    }

    /**
     * Save a participant.
     *
     * @param participantDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ParticipantDTO save(ParticipantDTO participantDTO) {
        log.debug("Request to save Participant : {}", participantDTO);
        Participant participant = participantMapper.toEntity(participantDTO);
        participant = participantRepository.save(participant);
        return participantMapper.toDto(participant);
    }

    /**
     * Get all the participants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParticipantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Participants");
        return participantRepository.findAll(pageable)
            .map(participantMapper::toDto);
    }


    /**
     * Get one participant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParticipantDTO> findOne(Long id) {
        log.debug("Request to get Participant : {}", id);
        return participantRepository.findById(id)
            .map(participantMapper::toDto);
    }

    /**
     * Delete the participant by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Participant : {}", id);
        participantRepository.deleteById(id);
    }

    @Override
    public Optional<Participant> findOne(String nhsNumber, LocalDate dateOfBirth) {
        IdentifiableData exampleIdentifiableData = new IdentifiableData();
        exampleIdentifiableData.setNhsNumber(nhsNumber);
        exampleIdentifiableData.setDateOfBirth(dateOfBirth);

        Participant exampleParticipant = new Participant();
        exampleParticipant.setIdentifiableData(exampleIdentifiableData);

        Example<Participant> example = Example.of(exampleParticipant);
        return participantRepository.findOne(example);
    }

    @Override
    public ParticipantExistsDTO exists(String nhsNumber, LocalDate dateOfBirth) {
        Optional<Participant> participantOptional = findOne(nhsNumber, dateOfBirth);

        if (!participantOptional.isPresent()) {
            return ParticipantExistsDTO.NOT_FOUND;
        }

        Participant participant = participantOptional.get();
        String email = participant.getIdentifiableData().getEmail();
        String password = participant.getUser().getPassword();

        if ((email != null) || (password != null)) {
            return ParticipantExistsDTO.ALREADY_REGISTERED;
        }

        return ParticipantExistsDTO.READY;
    }

    @Override
    public void activate(ParticipantActivationDTO participantActivationDTO) {
        String email = participantActivationDTO.getEmailAddress();
        if (
            identifiableDataService.findOne(
                email
            ).isPresent()
        ) {
            throw new EmailAlreadyUsedException();
        }

        User user = new User();
        user.setEmail(email);
        String rawPassword = participantActivationDTO.getPassword();
        String encryptedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encryptedPassword);
        String studyCode = participantActivationDTO.getStudyCode();
        user.setLogin(studyCode);
        user.setActivated(true);
        Set<Authority> authorities = new HashSet<Authority>();
        authorities.add(
            authorityRepository.getOne(
                RoleManager.PARTICIPANT
            )
        );
        user.setAuthorities(authorities);
        userRepository.save(user);
        AnswerResponse consentResponse = answerResponseService.saveDto(participantActivationDTO.getConsentResponse());
        consentResponse.setState(ResponseState.SUBMITTED);

        Optional<StudyId> studyIdOptional = studyIdService.getStudyIdByCode(studyCode);
        StudyId studyId = studyIdOptional.get();

        Procedure procedure = new Procedure();
        procedure.setConsentResponse(consentResponse);
        procedure.setRiskAssessmentResponse(studyId.getRiskAssessmentResponse());
        procedureService.save(procedure);

        Participant newParticipant = new Participant();
        newParticipant.setUser(user);
        newParticipant.setRegisterDatetime(Instant.now());
        newParticipant.setProcedure(procedure);
        newParticipant.setDateOfBirth(participantActivationDTO.getDateOfBirth());
        participantRepository.save(newParticipant);

        studyId.setParticipant(newParticipant);
        studyIdService.save(studyId);
    }

    @Override
    public void updateParticipantDetails(Principal principal, ParticipantDetailsDTO participantDetailsDTO) {
        Optional<Participant> participantOptional = participantRepository.findOneByUserLogin(principal.getName());
        Participant participant = participantOptional.get();

        IdentifiableData identifiableData = new IdentifiableData();
        identifiableData.setFirstname(participantDetailsDTO.getForename());
        identifiableData.setSurname(participantDetailsDTO.getSurname());
        identifiableData.setDateOfBirth(participant.getDateOfBirth());
        identifiableData.setAddress1(participantDetailsDTO.getAddressLine1());
        identifiableData.setAddress2(participantDetailsDTO.getAddressLine2());
        identifiableData.setAddress3(participantDetailsDTO.getAddressLine3());
        identifiableData.setAddress4(participantDetailsDTO.getAddressLine4());
        identifiableData.setAddress5(participantDetailsDTO.getAddressLine5());
        identifiableData.setPostcode(participantDetailsDTO.getPostCode());
        identifiableData.setHomePhoneNumber(participantDetailsDTO.getHomePhoneNumber());
        identifiableData.setMobilePhoneNumber(participantDetailsDTO.getMobilePhoneNumber());
        identifiableData.setEmail(participant.getUser().getEmail());
        identifiableData.setPreferContactWay(ParticipantContactWay.calculateCodes(participantDetailsDTO.getPreferredContactWays()));
        identifiableDataRepository.save(identifiableData);

        participant.setIdentifiableData(identifiableData);
        participantRepository.save(participant);
    }

    @Override
    public IdentifiableData getParticipantDetails(Principal principal) {
        Optional<Participant> participantOptional = participantRepository.findOneByUserLogin(principal.getName());
        Participant participant = participantOptional.get();

        return participant.getIdentifiableData();
    }
}
