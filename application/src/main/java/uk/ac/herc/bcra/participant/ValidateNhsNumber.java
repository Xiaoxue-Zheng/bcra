package uk.ac.herc.bcra.participant;

import com.univocity.parsers.conversions.Validator;
import org.apache.commons.lang.StringUtils;

public class ValidateNhsNumber implements Validator<String> {

    public String validate(String nhsNumber) {
        if (!isTenNumbers(nhsNumber)) {
            return "Bad NHS Number format: " + nhsNumber;
        }

        long sum = sumDigits(nhsNumber);
        long calculatedCheckDigit = calculateCheckDigit(sum);
        long stringCheckDigit = Integer.parseInt(
            nhsNumber.substring(9, 10)
        );
        
        if (calculatedCheckDigit == stringCheckDigit) {
            return null;
        }

        return "Invalid NHS Number: " + nhsNumber;
    }

    private boolean isTenNumbers(String nhsNumber) {
        return (
            (nhsNumber.length() == 10) &&
            StringUtils.isNumeric(nhsNumber)
        );
    }
	
	private long sumDigits(String nhsNumber) {
		long sum = 0;
		
        for (int digitIndex = 0; digitIndex <= 8; digitIndex++) {
            long digit = Long.parseLong(
                nhsNumber.substring(digitIndex, digitIndex + 1)
            );
            long factor = 10 - digitIndex;
            sum += (digit * factor);
        }
        
        return sum;
	}
	
	private long calculateCheckDigit(long sum) {
        long checkDigit = (11 - (sum % 11));
        
        if (checkDigit == 11) {
        	checkDigit = 0;
        }
        
        return checkDigit;
	}
}