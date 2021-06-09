package com.kyanite.article.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Article.
 */
@Entity
@Table(name = "article")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "time")
    private Instant time;

    @Column(name = "text")
    private String text;

    @Column(name = "contributors")
    private String contributors;

    @OneToMany(mappedBy = "article")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "article" }, allowSetters = true)
    private Set<Annex> annexes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "articles", "type", "ddUsers" }, allowSetters = true)
    private SubType subType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "creators", "modulePermissions", "subTypes" }, allowSetters = true)
    private DdUser ddUser;

    @OneToMany(mappedBy = "article")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "article" }, allowSetters = true)
    private Set<DdDept> ddDepts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Article id(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Article title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getTime() {
        return this.time;
    }

    public Article time(Instant time) {
        this.time = time;
        return this;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public String getText() {
        return this.text;
    }

    public Article text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getContributors() {
        return this.contributors;
    }

    public Article contributors(String contributors) {
        this.contributors = contributors;
        return this;
    }

    public void setContributors(String contributors) {
        this.contributors = contributors;
    }

    public Set<Annex> getAnnexes() {
        return this.annexes;
    }

    public Article annexes(Set<Annex> annexes) {
        this.setAnnexes(annexes);
        return this;
    }

    public Article addAnnex(Annex annex) {
        this.annexes.add(annex);
        annex.setArticle(this);
        return this;
    }

    public Article removeAnnex(Annex annex) {
        this.annexes.remove(annex);
        annex.setArticle(null);
        return this;
    }

    public void setAnnexes(Set<Annex> annexes) {
        if (this.annexes != null) {
            this.annexes.forEach(i -> i.setArticle(null));
        }
        if (annexes != null) {
            annexes.forEach(i -> i.setArticle(this));
        }
        this.annexes = annexes;
    }

    public SubType getSubType() {
        return this.subType;
    }

    public Article subType(SubType subType) {
        this.setSubType(subType);
        return this;
    }

    public void setSubType(SubType subType) {
        this.subType = subType;
    }

    public DdUser getDdUser() {
        return this.ddUser;
    }

    public Article ddUser(DdUser ddUser) {
        this.setDdUser(ddUser);
        return this;
    }

    public void setDdUser(DdUser ddUser) {
        this.ddUser = ddUser;
    }

    public Set<DdDept> getDdDepts() {
        return this.ddDepts;
    }

    public Article ddDepts(Set<DdDept> ddDepts) {
        this.setDdDepts(ddDepts);
        return this;
    }

    public Article addDdDept(DdDept ddDept) {
        this.ddDepts.add(ddDept);
        ddDept.setArticle(this);
        return this;
    }

    public Article removeDdDept(DdDept ddDept) {
        this.ddDepts.remove(ddDept);
        ddDept.setArticle(null);
        return this;
    }

    public void setDdDepts(Set<DdDept> ddDepts) {
        if (this.ddDepts != null) {
            this.ddDepts.forEach(i -> i.setArticle(null));
        }
        if (ddDepts != null) {
            ddDepts.forEach(i -> i.setArticle(this));
        }
        this.ddDepts = ddDepts;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Article)) {
            return false;
        }
        return id != null && id.equals(((Article) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Article{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", time='" + getTime() + "'" +
            ", text='" + getText() + "'" +
            ", contributors='" + getContributors() + "'" +
            "}";
    }
}
