package com.kyanite.article.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ModulePermission.
 */
@Entity
@Table(name = "module_permission")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ModulePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "enable")
    private Boolean enable;

    @ManyToMany(mappedBy = "modulePermissions")
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

    public ModulePermission id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public ModulePermission name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnable() {
        return this.enable;
    }

    public ModulePermission enable(Boolean enable) {
        this.enable = enable;
        return this;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Set<DdUser> getDdUsers() {
        return this.ddUsers;
    }

    public ModulePermission ddUsers(Set<DdUser> ddUsers) {
        this.setDdUsers(ddUsers);
        return this;
    }

    public ModulePermission addDdUser(DdUser ddUser) {
        this.ddUsers.add(ddUser);
        ddUser.getModulePermissions().add(this);
        return this;
    }

    public ModulePermission removeDdUser(DdUser ddUser) {
        this.ddUsers.remove(ddUser);
        ddUser.getModulePermissions().remove(this);
        return this;
    }

    public void setDdUsers(Set<DdUser> ddUsers) {
        if (this.ddUsers != null) {
            this.ddUsers.forEach(i -> i.removeModulePermission(this));
        }
        if (ddUsers != null) {
            ddUsers.forEach(i -> i.addModulePermission(this));
        }
        this.ddUsers = ddUsers;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModulePermission)) {
            return false;
        }
        return id != null && id.equals(((ModulePermission) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ModulePermission{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", enable='" + getEnable() + "'" +
            "}";
    }
}
