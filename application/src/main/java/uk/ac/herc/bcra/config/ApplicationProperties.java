package uk.ac.herc.bcra.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Bcra.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    private final Email email = new Email();

    private final AdminApp admin = new AdminApp();

    private final QuestionnaireApp questionnaire = new QuestionnaireApp();

    public AdminApp getAdmin() {
        return admin;
    }

    public QuestionnaireApp getQuestionnaire() {
        return questionnaire;
    }

    public Email getEmail() {
        return email;
    }

    public static class Email{
        private String from;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }
    }

    public static class AdminApp{
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class QuestionnaireApp{
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }
}
