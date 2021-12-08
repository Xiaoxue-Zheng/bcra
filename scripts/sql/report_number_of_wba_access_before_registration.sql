SELECT
    pi.identifier as page,
    pv.date as visit_date
FROM page_view pv
INNER JOIN page_identifier pi ON pi.id = pv.page_identifier_id
WHERE user_id IS NULL
ORDER BY pv.date;