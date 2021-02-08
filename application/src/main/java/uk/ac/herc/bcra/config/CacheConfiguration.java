package uk.ac.herc.bcra.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
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
            createCache(cm, uk.ac.herc.bcra.domain.PersistentToken.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.User.class.getName() + ".persistentTokens");
            createCache(cm, uk.ac.herc.bcra.domain.Questionnaire.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.Questionnaire.class.getName() + ".questionSections");
            createCache(cm, uk.ac.herc.bcra.domain.Questionnaire.class.getName() + ".answerResponses");
            createCache(cm, uk.ac.herc.bcra.domain.QuestionSection.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.QuestionSection.class.getName() + ".answerSections");
            createCache(cm, uk.ac.herc.bcra.domain.QuestionGroup.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.QuestionGroup.class.getName() + ".questionSections");
            createCache(cm, uk.ac.herc.bcra.domain.QuestionGroup.class.getName() + ".questions");
            createCache(cm, uk.ac.herc.bcra.domain.Question.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.Question.class.getName() + ".questionItems");
            createCache(cm, uk.ac.herc.bcra.domain.Question.class.getName() + ".answers");
            createCache(cm, uk.ac.herc.bcra.domain.QuestionItem.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.QuestionItem.class.getName() + ".answerItems");
            createCache(cm, uk.ac.herc.bcra.domain.AnswerResponse.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.AnswerResponse.class.getName() + ".answerSections");
            createCache(cm, uk.ac.herc.bcra.domain.AnswerSection.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.AnswerSection.class.getName() + ".answerGroups");
            createCache(cm, uk.ac.herc.bcra.domain.AnswerGroup.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.AnswerGroup.class.getName() + ".answers");
            createCache(cm, uk.ac.herc.bcra.domain.Answer.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.Answer.class.getName() + ".answerItems");
            createCache(cm, uk.ac.herc.bcra.domain.AnswerItem.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.DisplayCondition.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.QuestionSection.class.getName() + ".displayConditions");
            createCache(cm, uk.ac.herc.bcra.domain.Question.class.getName() + ".displayConditions");
            createCache(cm, uk.ac.herc.bcra.domain.Question.class.getName() + ".referralConditions");
            createCache(cm, uk.ac.herc.bcra.domain.ReferralCondition.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.CsvFile.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.Participant.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.IdentifiableData.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.CsvContent.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.Procedure.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.QuestionSection.class.getName() + ".referralConditions");
            createCache(cm, uk.ac.herc.bcra.domain.StudyId.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.RiskFactor.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.Risk.class.getName());
            createCache(cm, uk.ac.herc.bcra.domain.Risk.class.getName() + ".riskFactors");
            createCache(cm, uk.ac.herc.bcra.domain.RiskAssessmentResult.class.getName());
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
