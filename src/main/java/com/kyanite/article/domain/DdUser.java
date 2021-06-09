package com.kyanite.article.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DdUser.
 */
@Entity
@Table(name = "dd_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DdUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unionid")
    private String unionid;

    @Column(name = "remark")
    private String remark;

    @Column(name = "userid")
    private String userid;

    @Column(name = "is_leader_in_depts")
    private String isLeaderInDepts;

    @Column(name = "is_boss")
    private Boolean isBoss;

    @Column(name = "hired_date", precision = 21, scale = 2)
    private BigDecimal hiredDate;

    @Column(name = "is_senior")
    private Boolean isSenior;

    @Column(name = "tel")
    private String tel;

    @Column(name = "department")
    private String department;

    @Column(name = "work_place")
    private String workPlace;

    @Lob
    @Column(name = "order_in_depts")
    private String orderInDepts;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "errmsg")
    private String errmsg;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @Column(name = "is_hide")
    private Boolean isHide;

    @Column(name = "jobnumber")
    private String jobnumber;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "extattr")
    private String extattr;

    @Column(name = "state_code")
    private String stateCode;

    @Lob
    @Column(name = "position")
    private String position;

    @Column(name = "roles")
    private String roles;

    @OneToMany(mappedBy = "ddUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "annexes", "subType", "ddUser", "ddDepts" }, allowSetters = true)
    private Set<Article> creators = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_dd_user__module_permission",
        joinColumns = @JoinColumn(name = "dd_user_id"),
        inverseJoinColumns = @JoinColumn(name = "module_permission_id")
    )
    @JsonIgnoreProperties(value = { "ddUsers" }, allowSetters = true)
    private Set<ModulePermission> modulePermissions = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_dd_user__sub_type",
        joinColumns = @JoinColumn(name = "dd_user_id"),
        inverseJoinColumns = @JoinColumn(name = "sub_type_id")
    )
    @JsonIgnoreProperties(value = { "articles", "type", "ddUsers" }, allowSetters = true)
    private Set<SubType> subTypes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DdUser id(Long id) {
        this.id = id;
        return this;
    }

    public String getUnionid() {
        return this.unionid;
    }

    public DdUser unionid(String unionid) {
        this.unionid = unionid;
        return this;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getRemark() {
        return this.remark;
    }

    public DdUser remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserid() {
        return this.userid;
    }

    public DdUser userid(String userid) {
        this.userid = userid;
        return this;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getIsLeaderInDepts() {
        return this.isLeaderInDepts;
    }

    public DdUser isLeaderInDepts(String isLeaderInDepts) {
        this.isLeaderInDepts = isLeaderInDepts;
        return this;
    }

    public void setIsLeaderInDepts(String isLeaderInDepts) {
        this.isLeaderInDepts = isLeaderInDepts;
    }

    public Boolean getIsBoss() {
        return this.isBoss;
    }

    public DdUser isBoss(Boolean isBoss) {
        this.isBoss = isBoss;
        return this;
    }

    public void setIsBoss(Boolean isBoss) {
        this.isBoss = isBoss;
    }

    public BigDecimal getHiredDate() {
        return this.hiredDate;
    }

    public DdUser hiredDate(BigDecimal hiredDate) {
        this.hiredDate = hiredDate;
        return this;
    }

    public void setHiredDate(BigDecimal hiredDate) {
        this.hiredDate = hiredDate;
    }

    public Boolean getIsSenior() {
        return this.isSenior;
    }

    public DdUser isSenior(Boolean isSenior) {
        this.isSenior = isSenior;
        return this;
    }

    public void setIsSenior(Boolean isSenior) {
        this.isSenior = isSenior;
    }

    public String getTel() {
        return this.tel;
    }

    public DdUser tel(String tel) {
        this.tel = tel;
        return this;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDepartment() {
        return this.department;
    }

    public DdUser department(String department) {
        this.department = department;
        return this;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getWorkPlace() {
        return this.workPlace;
    }

    public DdUser workPlace(String workPlace) {
        this.workPlace = workPlace;
        return this;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getOrderInDepts() {
        return this.orderInDepts;
    }

    public DdUser orderInDepts(String orderInDepts) {
        this.orderInDepts = orderInDepts;
        return this;
    }

    public void setOrderInDepts(String orderInDepts) {
        this.orderInDepts = orderInDepts;
    }

    public String getMobile() {
        return this.mobile;
    }

    public DdUser mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getErrmsg() {
        return this.errmsg;
    }

    public DdUser errmsg(String errmsg) {
        this.errmsg = errmsg;
        return this;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Boolean getActive() {
        return this.active;
    }

    public DdUser active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public DdUser avatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getIsAdmin() {
        return this.isAdmin;
    }

    public DdUser isAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
        return this;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Boolean getIsHide() {
        return this.isHide;
    }

    public DdUser isHide(Boolean isHide) {
        this.isHide = isHide;
        return this;
    }

    public void setIsHide(Boolean isHide) {
        this.isHide = isHide;
    }

    public String getJobnumber() {
        return this.jobnumber;
    }

    public DdUser jobnumber(String jobnumber) {
        this.jobnumber = jobnumber;
        return this;
    }

    public void setJobnumber(String jobnumber) {
        this.jobnumber = jobnumber;
    }

    public String getName() {
        return this.name;
    }

    public DdUser name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtattr() {
        return this.extattr;
    }

    public DdUser extattr(String extattr) {
        this.extattr = extattr;
        return this;
    }

    public void setExtattr(String extattr) {
        this.extattr = extattr;
    }

    public String getStateCode() {
        return this.stateCode;
    }

    public DdUser stateCode(String stateCode) {
        this.stateCode = stateCode;
        return this;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getPosition() {
        return this.position;
    }

    public DdUser position(String position) {
        this.position = position;
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRoles() {
        return this.roles;
    }

    public DdUser roles(String roles) {
        this.roles = roles;
        return this;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Set<Article> getCreators() {
        return this.creators;
    }

    public DdUser creators(Set<Article> articles) {
        this.setCreators(articles);
        return this;
    }

    public DdUser addCreator(Article article) {
        this.creators.add(article);
        article.setDdUser(this);
        return this;
    }

    public DdUser removeCreator(Article article) {
        this.creators.remove(article);
        article.setDdUser(null);
        return this;
    }

    public void setCreators(Set<Article> articles) {
        if (this.creators != null) {
            this.creators.forEach(i -> i.setDdUser(null));
        }
        if (articles != null) {
            articles.forEach(i -> i.setDdUser(this));
        }
        this.creators = articles;
    }

    public Set<ModulePermission> getModulePermissions() {
        return this.modulePermissions;
    }

    public DdUser modulePermissions(Set<ModulePermission> modulePermissions) {
        this.setModulePermissions(modulePermissions);
        return this;
    }

    public DdUser addModulePermission(ModulePermission modulePermission) {
        this.modulePermissions.add(modulePermission);
        modulePermission.getDdUsers().add(this);
        return this;
    }

    public DdUser removeModulePermission(ModulePermission modulePermission) {
        this.modulePermissions.remove(modulePermission);
        modulePermission.getDdUsers().remove(this);
        return this;
    }

    public void setModulePermissions(Set<ModulePermission> modulePermissions) {
        this.modulePermissions = modulePermissions;
    }

    public Set<SubType> getSubTypes() {
        return this.subTypes;
    }

    public DdUser subTypes(Set<SubType> subTypes) {
        this.setSubTypes(subTypes);
        return this;
    }

    public DdUser addSubType(SubType subType) {
        this.subTypes.add(subType);
        subType.getDdUsers().add(this);
        return this;
    }

    public DdUser removeSubType(SubType subType) {
        this.subTypes.remove(subType);
        subType.getDdUsers().remove(this);
        return this;
    }

    public void setSubTypes(Set<SubType> subTypes) {
        this.subTypes = subTypes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DdUser)) {
            return false;
        }
        return id != null && id.equals(((DdUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DdUser{" +
            "id=" + getId() +
            ", unionid='" + getUnionid() + "'" +
            ", remark='" + getRemark() + "'" +
            ", userid='" + getUserid() + "'" +
            ", isLeaderInDepts='" + getIsLeaderInDepts() + "'" +
            ", isBoss='" + getIsBoss() + "'" +
            ", hiredDate=" + getHiredDate() +
            ", isSenior='" + getIsSenior() + "'" +
            ", tel='" + getTel() + "'" +
            ", department='" + getDepartment() + "'" +
            ", workPlace='" + getWorkPlace() + "'" +
            ", orderInDepts='" + getOrderInDepts() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", errmsg='" + getErrmsg() + "'" +
            ", active='" + getActive() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", isAdmin='" + getIsAdmin() + "'" +
            ", isHide='" + getIsHide() + "'" +
            ", jobnumber='" + getJobnumber() + "'" +
            ", name='" + getName() + "'" +
            ", extattr='" + getExtattr() + "'" +
            ", stateCode='" + getStateCode() + "'" +
            ", position='" + getPosition() + "'" +
            ", roles='" + getRoles() + "'" +
            "}";
    }
}
