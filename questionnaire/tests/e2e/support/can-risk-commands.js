Cypress.Commands.add('createCanRiskReportForParticipant', (studyCode) => {
    createDummyCanRiskFile(studyCode);
    return getStudyIdFromCode(studyCode).then((studyId) => {
        return getAdminUser().then((admin) => {
            return createCanRiskReportForStudyId(studyId, admin);
        });
    });
})

function getStudyIdFromCode(studyCode) {
    return cy.task('query', {
        sql: "SELECT * FROM study_id WHERE code = '" + studyCode + "'"
    }).then((response) => {
        return response.rows[0];
    });
}

function getAdminUser() {
    return cy.task('query', {
        sql: "SELECT * FROM jhi_user WHERE login = 'admin'"
    }).then((response) => {
        return response.rows[0];
    });
}

let _current_can_risk_report_id = 0;
function createCanRiskReportForStudyId(studyId, uploadedBy) {
    return cy.task('query', {
        sql: "INSERT INTO can_risk_report (id, filename, associated_study_id_id, uploaded_by_id) " +
            "VALUES(" + _current_can_risk_report_id + ",'"+ studyId + ".pdf', " + studyId.id + ", " + uploadedBy.id + ")"
    }).then((response) => {
        _current_can_risk_report_id += 1;
    });
}

function createDummyCanRiskFile(studyCode) {
    let dir = Cypress.platform.includes("Win")?'/home/canrisk/':'/usr/local/share/canrisk/'
    cy.writeFile(dir + studyCode + '.pdf', 'empty-file');
}

Cypress.Commands.add('deleteCanRiskReportForParticipant', (studyCode) => {
    return getStudyIdFromCode(studyCode).then((studyId) => {
        return deleteCanRiskReportForStudyId(studyId);
    });
})

function deleteCanRiskReportForStudyId(studyId) {
    return cy.task('query', {
        sql: "DELETE FROM can_risk_report where associated_study_id_id = " + studyId.id
    });
}
