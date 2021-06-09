package com.kyanite.article.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DdDept.
 */
@Entity
@Table(name = "dd_dept")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DdDept implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "parent_id")
    private String parentId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "annexes", "subType", "ddUser", "ddDepts" }, allowSetters = true)
    private Article article;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DdDept id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public DdDept name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return this.parentId;
    }

    public DdDept parentId(String parentId) {
        this.parentId = parentId;
        return this;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Article getArticle() {
        return this.article;
    }

    public DdDept article(Article article) {
        this.setArticle(article);
        return this;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DdDept)) {
            return false;
        }
        return id != null && id.equals(((DdDept) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DdDept{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", parentId='" + getParentId() + "'" +
            "}";
    }
}
