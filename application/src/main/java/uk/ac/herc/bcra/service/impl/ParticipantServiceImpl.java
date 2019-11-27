package uk.ac.herc.bcra.service.impl;

import uk.ac.herc.bcra.service.IdentifiableDataService;
import uk.ac.herc.bcra.service.ParticipantService;
import uk.ac.herc.bcra.domain.IdentifiableData;
import uk.ac.herc.bcra.domain.Participant;
import uk.ac.herc.bcra.domain.User;
import uk.ac.herc.bcra.repository.IdentifiableDataRepository;
import uk.ac.herc.bcra.repository.ParticipantRepository;
import uk.ac.herc.bcra.repository.UserRepository;
import uk.ac.herc.bcra.service.dto.ParticipantActivationDTO;
import uk.ac.herc.bcra.service.dto.ParticipantDTO;
import uk.ac.herc.bcra.service.dto.ParticipantExistsDTO;
import uk.ac.herc.bcra.service.mapper.ParticipantMapper;
import uk.ac.herc.bcra.web.rest.errors.EmailAlreadyUsedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

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

    public ParticipantServiceImpl(
        ParticipantRepository participantRepository,
        ParticipantMapper participantMapper,
        IdentifiableDataService identifiableDataService,
        IdentifiableDataRepository identifiableDataRepository,
        UserRepository userRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.participantRepository = participantRepository;
        this.participantMapper = participantMapper;
        this.identifiableDataService = identifiableDataService;
        this.identifiableDataRepository = identifiableDataRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
    public ParticipantExistsDTO exists(String nhsNumber, LocalDate dateOfBirth) {
        Optional<IdentifiableData> identifiableDataOptional =
            identifiableDataService.findOne(nhsNumber, dateOfBirth);

        if (!identifiableDataOptional.isPresent()) {
            return ParticipantExistsDTO.NOT_FOUND;
        }

        IdentifiableData identifiableData = identifiableDataOptional.get();
        String email =identifiableData.getEmail();
        String password = identifiableData.getParticipant().getUser().getPassword() ;

        if ((email != null) || (password != null)) {
            return ParticipantExistsDTO.ALREADY_REGISTERED;
        }

        return ParticipantExistsDTO.READY;
    }
    
    @Override
    public void activate(ParticipantActivationDTO participantActivationDTO) {

        if (
            identifiableDataService.findOne(
                participantActivationDTO.getEmailAddress()
            ).isPresent()
        ) {
            throw new EmailAlreadyUsedException();
        }

        Optional<IdentifiableData> identifiableDataOptional =
            identifiableDataService.findOne(
                participantActivationDTO.getNhsNumber(),
                participantActivationDTO.getDateOfBirth()
            );

        if (!identifiableDataOptional.isPresent()) {
            throw new RuntimeException(
                "Participant does not exist. "
                + " nhsNumber: " + participantActivationDTO.getNhsNumber()
                + " dateofBirth: " + participantActivationDTO.getDateOfBirth()
            );
        }

        IdentifiableData identifiableData = identifiableDataOptional.get();
        Participant participant = identifiableData.getParticipant();
        User user = participant.getUser();

        if (
            (identifiableData.getEmail() != null) ||
            (user.getPassword() != null)
        ) {
            throw new RuntimeException(
                "Participant already activated. "
                + " nhsNumber: " + participantActivationDTO.getNhsNumber()
                + " dateofBirth: " + participantActivationDTO.getDateOfBirth()
            );
        }

        identifiableData.setEmail(participantActivationDTO.getEmailAddress());
        identifiableDataRepository.save(identifiableData);

        participant.setRegisterDatetime(Instant.now());
        participantRepository.save(participant);

        String rawPassword = participantActivationDTO.getPassword();
        String encryptedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }
}
