SELECT
    u.login as username,
    p.date_of_birth as date_of_birth
FROM jhi_user u
INNER JOIN participant p ON p.user_id = u.id;