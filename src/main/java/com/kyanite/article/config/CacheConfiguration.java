package com.kyanite.article.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.kyanite.article.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.kyanite.article.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.kyanite.article.domain.User.class.getName());
            createCache(cm, com.kyanite.article.domain.Authority.class.getName());
            createCache(cm, com.kyanite.article.domain.User.class.getName() + ".authorities");
            createCache(cm, com.kyanite.article.domain.Article.class.getName());
            createCache(cm, com.kyanite.article.domain.Article.class.getName() + ".annexes");
            createCache(cm, com.kyanite.article.domain.Article.class.getName() + ".ddDepts");
            createCache(cm, com.kyanite.article.domain.Banner.class.getName());
            createCache(cm, com.kyanite.article.domain.Banner.class.getName() + ".photos");
            createCache(cm, com.kyanite.article.domain.Type.class.getName());
            createCache(cm, com.kyanite.article.domain.Type.class.getName() + ".subTypes");
            createCache(cm, com.kyanite.article.domain.SubType.class.getName());
            createCache(cm, com.kyanite.article.domain.SubType.class.getName() + ".articles");
            createCache(cm, com.kyanite.article.domain.SubType.class.getName() + ".ddUsers");
            createCache(cm, com.kyanite.article.domain.Annex.class.getName());
            createCache(cm, com.kyanite.article.domain.Photo.class.getName());
            createCache(cm, com.kyanite.article.domain.ModulePermission.class.getName());
            createCache(cm, com.kyanite.article.domain.ModulePermission.class.getName() + ".ddUsers");
            createCache(cm, com.kyanite.article.domain.DdDept.class.getName());
            createCache(cm, com.kyanite.article.domain.DdUser.class.getName());
            createCache(cm, com.kyanite.article.domain.DdUser.class.getName() + ".creators");
            createCache(cm, com.kyanite.article.domain.DdUser.class.getName() + ".modulePermissions");
            createCache(cm, com.kyanite.article.domain.DdUser.class.getName() + ".subTypes");
            createCache(cm, com.kyanite.article.domain.Msg.class.getName());
            createCache(cm, com.kyanite.article.domain.Msg.class.getName() + ".msgTasks");
            createCache(cm, com.kyanite.article.domain.MsgTask.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
