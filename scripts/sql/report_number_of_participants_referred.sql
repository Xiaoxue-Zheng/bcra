SELECT 
    u.login as username,
    ra.state as referral_state
FROM study_id si
INNER JOIN participant p ON p.id = si.participant_id
INNER JOIN jhi_user u ON u.id = p.user_id
INNER JOIN answer_response ra ON si.risk_assessment_response_id = ra.id
WHERE ra.state = 'REFERRED';