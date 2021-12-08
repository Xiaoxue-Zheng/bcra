SELECT 
    u.login as username,
    cr.state as consent_state
FROM study_id si
INNER JOIN participant p ON p.id = si.participant_id
INNER JOIN jhi_user u ON u.id = p.user_id
INNER JOIN answer_response cr ON si.consent_response_id = cr.id
WHERE cr.state IN ('SUBMITTED', 'VALIDATED', 'PROCESSED');