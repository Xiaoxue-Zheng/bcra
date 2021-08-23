package uk.ac.herc.bcra.questionnaire;
public class AnswerCheck {
    boolean valid;
    String reason;
    public AnswerCheck(Boolean valid) {
        this.valid = valid;
    }

    public AnswerCheck(String reason) {
        this.valid = false;
        this.reason = reason;
    }

    public boolean isValid() {
        return valid;
    }

    public String getReason() {
        return reason;
    }
}
