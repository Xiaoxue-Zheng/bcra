const { rows } = require("pg/lib/defaults")

const DEFAULT_PASSWORD_HASH = '$2a$10$xfxxf5eZbLo0S70V55c8FO6R.741QpR4Lkh84749m/B7kP6/XIFc2'

Cypress.Commands.add('deleteParticipants', (participantLogins) => {
    return deleteNextParticipant(participantLogins, 0)
})

function deleteNextParticipant(participantLogins, currentLoginIx) {
    const login = participantLogins[currentLoginIx]
    return getParticipantByLogin(login).then((participant) => {
        return unassignParticipantFromStudyId(login).then(() => {
            return deleteParticipant(participant.id).then(() => {
                return deleteIdentifiableData(participant.identifiable_data_id).then(() => {
                        return deleteUserAuthority(participant.user_id).then(() => {
                            return deleteUser(login).then(() => {
                                if (currentLoginIx == participantLogins.length-1) {
                                    return 'DONE'
                                } else {
                                    return deleteNextParticipant(participantLogins, currentLoginIx+1)
                                }
                            })
                      })
                })
            })
        })
    })
}

function getParticipantByLogin(login) {
    return cy.task('query', {
        sql: `
            SELECT p.* FROM participant p
            INNER JOIN jhi_user u ON u.id = p.user_id
            WHERE u.login = $1
        `,
        values: [login]
    }).then((result) => {
        return result.rows[0]
    })
}

function unassignParticipantFromStudyId(login) {
    return cy.task('query', {
        sql: `
            UPDATE study_id
            SET participant_id = null
            WHERE code = $1
        `,
        values: [login]
    })
}

function deleteIdentifiableData(identifiable_data_id) {
    return cy.task('query', {
        sql: `
            DELETE FROM identifiable_data
            WHERE id = $1
        `,
        values: [identifiable_data_id]
    })
}

function deleteParticipant(participantId) {
    return cy.task('query', {
        sql: `
            DELETE FROM participant p
            WHERE p.id = $1
        `,
        values: [participantId]
    })
}

function deleteUser(login) {
    return cy.task('query', {
        sql: `
            DELETE FROM jhi_user u
            WHERE u.login = $1
        `,
        values: [login]
    })
}

function deleteUserAuthority(userId) {
    return cy.task('query', {
        sql: `
            DELETE FROM jhi_user_authority u
            WHERE u.user_id = $1
        `,
        values: [userId]
    })
}

Cypress.Commands.add('registerPatientWithStudyCodeAndEmail', (studyCode, email) => {
    return getStudyIdFromStudyCode(studyCode).then((studyId) => {
            return createUserFromStudyIdAndEmail(studyId, email).then((user) => {
                return createParticipantFromUser(user, studyId).then((participant) => {
                    return assignParticipantToStudyId(participant, studyId)
                })
            })
    })
})

function getStudyIdFromStudyCode(studyCode) {
    return cy.task('query', {
        sql: `
            SELECT * FROM study_id
            WHERE code = $1
        `,
        values: [studyCode]
    }).then((studyIdRes) => {
        return studyIdRes.rows[0]
    })
}

function createUserFromStudyIdAndEmail(studyId, email) {
    return getNextIdForTable('jhi_user').then((next_id) => {
        return cy.task('query', {
            sql: `
                INSERT INTO jhi_user
                (id, login, password_hash, email, activated, created_by)
                VALUES($1, $2, $3, $4, true, 1)
            `,
            values: [next_id, studyId.code, DEFAULT_PASSWORD_HASH, email]
        }).then(() => {
            return cy.task('query', {
                sql: `
                    SELECT * FROM jhi_user
                    WHERE id = $1
                `,
                values: [next_id]
            }).then((result) => {
                return result.rows[0]
            })
        })
    })
}

function createParticipantFromUser(user, studyId) {
    return getNextIdForTable('participant').then((next_id) => {
        return cy.task('query', {
            sql: `
                INSERT INTO participant
                (id, user_id, date_of_birth, status, study_id_id)
                VALUES($1, $2, '01/01/1990', 'IN_PROGRESS', $3)
            `,
            values: [next_id, user.id, studyId.id]
        }).then(() => {
            return cy.task('query', {
                sql: `
                    SELECT * FROM participant
                    WHERE id = $1
                `,
                values: [next_id]
            }).then((result) => {
                return result.rows[0]
            })
        })
    })
}

function assignParticipantToStudyId(participant, studyId) {
    return cy.task('query', {
        sql: `
            UPDATE study_id
            SET participant_id = $1
            WHERE id = $2
        `,
        values: [participant.id, studyId.id]
    }).then((result) => {
        return result.rows[0]
    })
}

function getNextIdForTable(tableName) {
    return cy.task('query', {
        sql: "SELECT MAX(id) FROM " + tableName
    }).then((result) => {
        if (result.rows[0].max)
            return parseInt(result.rows[0].max) + 1
        else
            return 1
    })
}

Cypress.Commands.add('updateParticipantDetails', (studyCode) => {
    return getUserByStudyCode(studyCode).then((user) => {
        return getParticipantByLogin(studyCode).then((participant) => {
            return createIdentifiableDataFromUser(user).then((identifiableData) => {
                return assignIdentifiableDataToParticipant(identifiableData, participant)
            })
        })
    })
})

function getUserByStudyCode(studyCode) {
    return cy.task('query', {
        sql: `
            SELECT * FROM jhi_user
            WHERE login = $1
        `,
        values: [studyCode]
    }).then((result) => {
        return result.rows[0]
    })
}

function createIdentifiableDataFromUser(user) {
    return getNextIdForTable('identifiable_data').then((next_id) => {
        return cy.task('query', {
            sql: `
                INSERT INTO identifiable_data
                (id, date_of_birth, email, firstname, surname, address_1, postcode, prefer_contact_way)
                VALUES ($1, '01/01/1990', $2, 'John', 'Doe', '123 Fake Street', 'AA1 1AA', 1)
            `,
            values: [next_id, user.email]
        }).then(() => {
            return cy.task('query', {
                sql: `
                    SELECT * FROM identifiable_data
                    WHERE id = $1
                `,
                values: [next_id]
            }).then((result) => {
                return result.rows[0]
            })
        })
    })
}

function assignIdentifiableDataToParticipant(identifiableData, participant) {
    return cy.task('query', {
        sql: `
            UPDATE participant
            SET identifiable_data_id = $1
            WHERE id = $2
        `,
        values: [identifiableData.id, participant.id]
    })
}

Cypress.Commands.add('removeIdentifiableDataForParticipant', (studyCode) => {
    return getUserByStudyCode(studyCode).then((user) => {
        return getParticipantByLogin(studyCode).then((participant) => {
            return unassignParticipantFromIdentifiableData(participant).then((identifiableDataId) => {
                return deleteIdentifiableDataById(identifiableDataId)
            })
        })
    })
})

function unassignParticipantFromIdentifiableData(participant) {
    return cy.task('query', {
        sql: `
            UPDATE participant
            SET identifiable_data_id = null
            WHERE id = $1
        `,
        values: [participant.id]
    }).then(() => {
        return participant.identifiable_data_id
    })
}

function deleteIdentifiableDataById(identifiableDataId) {
    return cy.task('query', {
        sql: `
            DELETE FROM identifiable_data
            WHERE id = $1
        `,
        values: [identifiableDataId]
    })
}

Cypress.Commands.add('updateAccountResetKey', (email, key) => {
  return updateAccountResetKeyByEmail(email, key)
})

function updateAccountResetKeyByEmail(email, key){
  return cy.task('query', {
    sql: `
            UPDATE jhi_user
            SET reset_key = $2, reset_date = $3
            WHERE email = $1
        `,
    values: [email, key, new Date().toISOString()]
  })
}


