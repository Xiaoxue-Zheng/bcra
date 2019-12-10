package uk.ac.herc.bcra.participant;

import com.univocity.parsers.conversions.Validator;

/*
    GOV_UK_POSTCODE_REGEX 
    is from:
    
    https://assets.publishing.service.gov.uk
    /government
    /uploads
    /system
    /uploads
    /attachment_data
    /file
    /488478
    /Bulk_Data_Transfer_-_additional_validation_valid_from_12_November_2015.pdf
*/
public class ValidatePostcode implements Validator<String> {

    private final String GOV_UK_POSTCODE_REGEX =
        "^([Gg][Ii][Rr] 0[Aa]{2})|((([A-Za-z][0-9]{1,2})|"
        + "(([A-Za-z][A-Ha-hJ-Yj-y][0-9]{1,2})|(([AZa-z][0-9][A-Za-z])|"
        + "([A-Za-z][A-Ha-hJ-Yj-y][0-9]?[A-Za-z])))) [0-9][A-Za-z]{2})$";

    public String validate(String postcode) {
        if (postcode.matches(GOV_UK_POSTCODE_REGEX)) {
            return null;
        }

        return "Invalid postcode: " + postcode;
    }
}