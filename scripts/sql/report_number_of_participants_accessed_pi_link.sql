SELECT
    u.login as username,
    pi.identifier as page,
    pv.date as date_visited
FROM page_view pv
INNER JOIN jhi_user u ON pv.user_id = u.id
INNER JOIN page_identifier pi ON pi.id = pv.page_identifier_id
WHERE pi.identifier = 'PI_LINK';