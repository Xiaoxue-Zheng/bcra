SELECT DISTINCT
    u.login as username,
    u.email as email
FROM jhi_user u
INNER JOIN jhi_user_authority ua ON ua.user_id = u.id
WHERE ua.authority_name NOT IN ('ROLE_ADMIN')
ORDER BY u.login;