const moment = require('moment')

export const DateService = {
    isDateOfBirthInAgeRange (dob, minAgeYears, maxAgeYears) {
        dob = moment(dob);
        const earliestBirthDate = getDateNYearsAgo(maxAgeYears)
        const latestBirthDate = this.getDateNYearsAgo(minAgeYears)
        return dob.isBetween(earliestBirthDate, latestBirthDate);
    },

    getDateNYearsAgo (yearsAgo) {
        return moment().subtract(yearsAgo, 'years')
    }
}
