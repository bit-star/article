package com.kyanite.article.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Banner.
 */
@Entity
@Table(name = "banner")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Banner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "cover_url")
    private String coverUrl;

    @Column(name = "name")
    private String name;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "specifications")
    private String specifications;

    @Column(name = "is_export")
    private Boolean isExport;

    @Column(name = "supplier")
    private String supplier;

    @Column(name = "supplier_address")
    private String supplierAddress;

    @Column(name = "supplier_capacity")
    private String supplierCapacity;

    @OneToMany(mappedBy = "banner")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "banner" }, allowSetters = true)
    private Set<Photo> photos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Banner id(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Banner title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverUrl() {
        return this.coverUrl;
    }

    public Banner coverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
        return this;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getName() {
        return this.name;
    }

    public Banner name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return this.brand;
    }

    public Banner brand(String brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return this.model;
    }

    public Banner model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSpecifications() {
        return this.specifications;
    }

    public Banner specifications(String specifications) {
        this.specifications = specifications;
        return this;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public Boolean getIsExport() {
        return this.isExport;
    }

    public Banner isExport(Boolean isExport) {
        this.isExport = isExport;
        return this;
    }

    public void setIsExport(Boolean isExport) {
        this.isExport = isExport;
    }

    public String getSupplier() {
        return this.supplier;
    }

    public Banner supplier(String supplier) {
        this.supplier = supplier;
        return this;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSupplierAddress() {
        return this.supplierAddress;
    }

    public Banner supplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
        return this;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getSupplierCapacity() {
        return this.supplierCapacity;
    }

    public Banner supplierCapacity(String supplierCapacity) {
        this.supplierCapacity = supplierCapacity;
        return this;
    }

    public void setSupplierCapacity(String supplierCapacity) {
        this.supplierCapacity = supplierCapacity;
    }

    public Set<Photo> getPhotos() {
        return this.photos;
    }

    public Banner photos(Set<Photo> photos) {
        this.setPhotos(photos);
        return this;
    }

    public Banner addPhoto(Photo photo) {
        this.photos.add(photo);
        photo.setBanner(this);
        return this;
    }

    public Banner removePhoto(Photo photo) {
        this.photos.remove(photo);
        photo.setBanner(null);
        return this;
    }

    public void setPhotos(Set<Photo> photos) {
        if (this.photos != null) {
            this.photos.forEach(i -> i.setBanner(null));
        }
        if (photos != null) {
            photos.forEach(i -> i.setBanner(this));
        }
        this.photos = photos;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Banner)) {
            return false;
        }
        return id != null && id.equals(((Banner) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Banner{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", coverUrl='" + getCoverUrl() + "'" +
            ", name='" + getName() + "'" +
            ", brand='" + getBrand() + "'" +
            ", model='" + getModel() + "'" +
            ", specifications='" + getSpecifications() + "'" +
            ", isExport='" + getIsExport() + "'" +
            ", supplier='" + getSupplier() + "'" +
            ", supplierAddress='" + getSupplierAddress() + "'" +
            ", supplierCapacity='" + getSupplierCapacity() + "'" +
            "}";
    }
}
