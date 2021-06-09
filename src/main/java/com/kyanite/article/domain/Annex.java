package com.kyanite.article.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kyanite.article.domain.enumeration.StorageMode;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Annex.
 */
@Entity
@Table(name = "annex")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Annex implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "space_id")
    private String spaceId;

    @Column(name = "file_id")
    private String fileId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_size")
    private String fileSize;

    @Column(name = "file_type")
    private String fileType;

    @Enumerated(EnumType.STRING)
    @Column(name = "storage_mode")
    private StorageMode storageMode;

    @Column(name = "url")
    private String url;

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

    public Annex id(Long id) {
        this.id = id;
        return this;
    }

    public String getSpaceId() {
        return this.spaceId;
    }

    public Annex spaceId(String spaceId) {
        this.spaceId = spaceId;
        return this;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getFileId() {
        return this.fileId;
    }

    public Annex fileId(String fileId) {
        this.fileId = fileId;
        return this;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return this.fileName;
    }

    public Annex fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return this.fileSize;
    }

    public Annex fileSize(String fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return this.fileType;
    }

    public Annex fileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public StorageMode getStorageMode() {
        return this.storageMode;
    }

    public Annex storageMode(StorageMode storageMode) {
        this.storageMode = storageMode;
        return this;
    }

    public void setStorageMode(StorageMode storageMode) {
        this.storageMode = storageMode;
    }

    public String getUrl() {
        return this.url;
    }

    public Annex url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Article getArticle() {
        return this.article;
    }

    public Annex article(Article article) {
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
        if (!(o instanceof Annex)) {
            return false;
        }
        return id != null && id.equals(((Annex) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Annex{" +
            "id=" + getId() +
            ", spaceId='" + getSpaceId() + "'" +
            ", fileId='" + getFileId() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", fileSize='" + getFileSize() + "'" +
            ", fileType='" + getFileType() + "'" +
            ", storageMode='" + getStorageMode() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
