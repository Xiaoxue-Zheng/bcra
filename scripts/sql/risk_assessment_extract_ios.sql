COPY (
	SELECT
		u.login as user_login,
		
		ir.id as individual_risk_id,
		ir.lifetime_risk as indi_lifetime_risk,
		ir.prob_not_bcra as indi_prob_not_bcra_gene,
		ir.prob_bcra_1 as indi_prob_bcra_1_gene,
		ir.prob_bcra_2 as indi_prob_bcra_2_gene,

		(SELECT risk_factor FROM yearly_risk WHERE risk_id = ir.id AND year = 1) as indi_factor_1,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = ir.id AND year = 2) as indi_factor_2,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = ir.id AND year = 3) as indi_factor_3,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = ir.id AND year = 4) as indi_factor_4,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = ir.id AND year = 5) as indi_factor_5,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = ir.id AND year = 6) as indi_factor_6,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = ir.id AND year = 7) as indi_factor_7,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = ir.id AND year = 8) as indi_factor_8,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = ir.id AND year = 9) as indi_factor_9,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = ir.id AND year = 10) as indi_factor_10,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = ir.id AND year = 11) as indi_factor_11,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = ir.id AND year = 12) as indi_factor_12,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = ir.id AND year = 13) as indi_factor_13,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = ir.id AND year = 14) as indi_factor_14,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = ir.id AND year = 15) as indi_factor_15,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = ir.id AND year = 16) as indi_factor_16,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = ir.id AND year = 17) as indi_factor_17,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = ir.id AND year = 18) as indi_factor_18,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = ir.id AND year = 19) as indi_factor_19,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = ir.id AND year = 20) as indi_factor_20,

		pr.id as population_risk_id,
		pr.lifetime_risk as pop_lifetime_risk,
		pr.prob_not_bcra as pop_prob_not_bcra_gene,
		pr.prob_bcra_1 as pop_prob_bcra_1_gene,
		pr.prob_bcra_2 as pop_prob_bcra_2_gene,

		(SELECT risk_factor FROM yearly_risk WHERE risk_id = pr.id AND year = 1) as pop_factor_1,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = pr.id AND year = 2) as pop_factor_2,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = pr.id AND year = 3) as pop_factor_3,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = pr.id AND year = 4) as pop_factor_4,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = pr.id AND year = 5) as pop_factor_5,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = pr.id AND year = 6) as pop_factor_6,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = pr.id AND year = 7) as pop_factor_7,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = pr.id AND year = 8) as pop_factor_8,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = pr.id AND year = 9) as pop_factor_9,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = pr.id AND year = 10) as pop_factor_10,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = pr.id AND year = 11) as pop_factor_11,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = pr.id AND year = 12) as pop_factor_12,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = pr.id AND year = 13) as pop_factor_13,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = pr.id AND year = 14) as pop_factor_14,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = pr.id AND year = 15) as pop_factor_15,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = pr.id AND year = 16) as pop_factor_16,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = pr.id AND year = 17) as pop_factor_17,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = pr.id AND year = 18) as pop_factor_18,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = pr.id AND year = 19) as pop_factor_19,
		(SELECT risk_factor FROM yearly_risk WHERE risk_id = pr.id AND year = 20) as pop_factor_20

	FROM risk_assessment_result rar
	INNER JOIN participant p ON p.id = rar.participant_id
	INNER JOIN jhi_user u ON u.id = p.user_id
	INNER JOIN risk ir ON ir.id = rar.individual_risk_id
	INNER JOIN risk pr ON pr.id = rar.population_risk_id
) TO '/usr/local/share/tyrercuzick/extract/tyrer_cuzick_extract.csv' WITH CSV DELIMITER ',' HEADER;
