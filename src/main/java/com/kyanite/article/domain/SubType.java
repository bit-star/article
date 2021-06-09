package com.kyanite.article.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SubType.
 */
@Entity
@Table(name = "sub_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SubType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "subType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "annexes", "subType", "ddUser", "ddDepts" }, allowSetters = true)
    private Set<Article> articles = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "subTypes" }, allowSetters = true)
    private Type type;

    @ManyToMany(mappedBy = "subTypes")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "creators", "modulePermissions", "subTypes" }, allowSetters = true)
    private Set<DdUser> ddUsers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SubType id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public SubType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Article> getArticles() {
        return this.articles;
    }

    public SubType articles(Set<Article> articles) {
        this.setArticles(articles);
        return this;
    }

    public SubType addArticle(Article article) {
        this.articles.add(article);
        article.setSubType(this);
        return this;
    }

    public SubType removeArticle(Article article) {
        this.articles.remove(article);
        article.setSubType(null);
        return this;
    }

    public void setArticles(Set<Article> articles) {
        if (this.articles != null) {
            this.articles.forEach(i -> i.setSubType(null));
        }
        if (articles != null) {
            articles.forEach(i -> i.setSubType(this));
        }
        this.articles = articles;
    }

    public Type getType() {
        return this.type;
    }

    public SubType type(Type type) {
        this.setType(type);
        return this;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Set<DdUser> getDdUsers() {
        return this.ddUsers;
    }

    public SubType ddUsers(Set<DdUser> ddUsers) {
        this.setDdUsers(ddUsers);
        return this;
    }

    public SubType addDdUser(DdUser ddUser) {
        this.ddUsers.add(ddUser);
        ddUser.getSubTypes().add(this);
        return this;
    }

    public SubType removeDdUser(DdUser ddUser) {
        this.ddUsers.remove(ddUser);
        ddUser.getSubTypes().remove(this);
        return this;
    }

    public void setDdUsers(Set<DdUser> ddUsers) {
        if (this.ddUsers != null) {
            this.ddUsers.forEach(i -> i.removeSubType(this));
        }
        if (ddUsers != null) {
            ddUsers.forEach(i -> i.addSubType(this));
        }
        this.ddUsers = ddUsers;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubType)) {
            return false;
        }
        return id != null && id.equals(((SubType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
