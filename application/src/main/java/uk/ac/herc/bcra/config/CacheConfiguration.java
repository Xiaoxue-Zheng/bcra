package uk.ac.herc.bcra.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, uk.ac.herc.bcra.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, uk.ac.herc.bcra.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, uk.ac.herc.bcra.domain.User.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.Authority.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.User.class.getName() + ".authorities");
            createCache(cm, uk.ac.herc.bcra.domain.Questionnaire.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.Questionnaire.class.getName() + ".answerResponses");
            createCache(cm, uk.ac.herc.bcra.domain.QuestionGroup.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.QuestionGroup.class.getName() + ".displayConditions");
            createCache(cm, uk.ac.herc.bcra.domain.QuestionGroup.class.getName() + ".answerGroups");
            createCache(cm, uk.ac.herc.bcra.domain.QuestionnaireQuestionGroup.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.QuestionnaireQuestionGroup.class.getName() + ".questionnaires");
            createCache(cm, uk.ac.herc.bcra.domain.QuestionnaireQuestionGroup.class.getName() + ".questionGroups");
            createCache(cm, uk.ac.herc.bcra.domain.Question.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.Question.class.getName() + ".answers");
            createCache(cm, uk.ac.herc.bcra.domain.QuestionGroupQuestion.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.QuestionGroupQuestion.class.getName() + ".questionGroups");
            createCache(cm, uk.ac.herc.bcra.domain.QuestionGroupQuestion.class.getName() + ".questions");
            createCache(cm, uk.ac.herc.bcra.domain.CheckboxQuestion.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.CheckboxQuestion.class.getName() + ".checkboxQuestionItems");
            createCache(cm, uk.ac.herc.bcra.domain.CheckboxQuestionItem.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.CheckboxQuestionItem.class.getName() + ".checkboxAnswerItems");
            createCache(cm, uk.ac.herc.bcra.domain.CheckboxQuestionItem.class.getName() + ".checkboxDisplayConditions");
            createCache(cm, uk.ac.herc.bcra.domain.RadioQuestion.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.RadioQuestion.class.getName() + ".radioQuestionItems");
            createCache(cm, uk.ac.herc.bcra.domain.RadioQuestionItem.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.NumberCheckboxQuestion.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.RepeatQuestion.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.RepeatQuestion.class.getName() + ".repeatDisplayConditions");
            createCache(cm, uk.ac.herc.bcra.domain.AnswerResponse.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.AnswerResponse.class.getName() + ".answerGroups");
            createCache(cm, uk.ac.herc.bcra.domain.AnswerGroup.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.AnswerGroup.class.getName() + ".answers");
            createCache(cm, uk.ac.herc.bcra.domain.Answer.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.CheckboxAnswer.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.CheckboxAnswer.class.getName() + ".checkboxAnswerItems");
            createCache(cm, uk.ac.herc.bcra.domain.CheckboxAnswerItem.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.RadioAnswer.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.RadioAnswerItem.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.NumberCheckboxAnswer.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.RepeatAnswer.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.DisplayCondition.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.CheckboxDisplayCondition.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.RepeatDisplayCondition.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.Questionnaire.class.getName() + ".questionnaireQuestionGroups");
            createCache(cm, uk.ac.herc.bcra.domain.QuestionGroup.class.getName() + ".questionnaireQuestionGroups");
            createCache(cm, uk.ac.herc.bcra.domain.QuestionGroup.class.getName() + ".questionGroupQuestions");
            createCache(cm, uk.ac.herc.bcra.domain.Question.class.getName() + ".questionGroupQuestions");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }
}
