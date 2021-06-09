package com.kyanite.article.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kyanite.article.domain.enumeration.DdMessageType;
import com.kyanite.article.domain.enumeration.MessageStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Msg.
 */
@Entity
@Table(name = "msg")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Msg implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dept_id_list")
    private String deptIdList;

    @Column(name = "userid_list")
    private String useridList;

    @Column(name = "to_all_user")
    private Boolean toAllUser;

    @Column(name = "progress_in_percent")
    private Integer progressInPercent;

    @Column(name = "title")
    private String title;

    @Column(name = "single_title")
    private String singleTitle;

    @Column(name = "single_url")
    private String singleUrl;

    @Column(name = "cover_url")
    private String coverUrl;

    @Column(name = "number_of_shards")
    private Integer numberOfShards;

    @Column(name = "complete_sharding")
    private Boolean completeSharding;

    @Column(name = "msg")
    private String msg;

    @Column(name = "execute_time")
    private Instant executeTime;

    @Column(name = "agent_id")
    private Long agentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private DdMessageType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private MessageStatus status;

    @OneToMany(mappedBy = "msg")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "msg" }, allowSetters = true)
    private Set<MsgTask> msgTasks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Msg id(Long id) {
        this.id = id;
        return this;
    }

    public String getDeptIdList() {
        return this.deptIdList;
    }

    public Msg deptIdList(String deptIdList) {
        this.deptIdList = deptIdList;
        return this;
    }

    public void setDeptIdList(String deptIdList) {
        this.deptIdList = deptIdList;
    }

    public String getUseridList() {
        return this.useridList;
    }

    public Msg useridList(String useridList) {
        this.useridList = useridList;
        return this;
    }

    public void setUseridList(String useridList) {
        this.useridList = useridList;
    }

    public Boolean getToAllUser() {
        return this.toAllUser;
    }

    public Msg toAllUser(Boolean toAllUser) {
        this.toAllUser = toAllUser;
        return this;
    }

    public void setToAllUser(Boolean toAllUser) {
        this.toAllUser = toAllUser;
    }

    public Integer getProgressInPercent() {
        return this.progressInPercent;
    }

    public Msg progressInPercent(Integer progressInPercent) {
        this.progressInPercent = progressInPercent;
        return this;
    }

    public void setProgressInPercent(Integer progressInPercent) {
        this.progressInPercent = progressInPercent;
    }

    public String getTitle() {
        return this.title;
    }

    public Msg title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSingleTitle() {
        return this.singleTitle;
    }

    public Msg singleTitle(String singleTitle) {
        this.singleTitle = singleTitle;
        return this;
    }

    public void setSingleTitle(String singleTitle) {
        this.singleTitle = singleTitle;
    }

    public String getSingleUrl() {
        return this.singleUrl;
    }

    public Msg singleUrl(String singleUrl) {
        this.singleUrl = singleUrl;
        return this;
    }

    public void setSingleUrl(String singleUrl) {
        this.singleUrl = singleUrl;
    }

    public String getCoverUrl() {
        return this.coverUrl;
    }

    public Msg coverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
        return this;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Integer getNumberOfShards() {
        return this.numberOfShards;
    }

    public Msg numberOfShards(Integer numberOfShards) {
        this.numberOfShards = numberOfShards;
        return this;
    }

    public void setNumberOfShards(Integer numberOfShards) {
        this.numberOfShards = numberOfShards;
    }

    public Boolean getCompleteSharding() {
        return this.completeSharding;
    }

    public Msg completeSharding(Boolean completeSharding) {
        this.completeSharding = completeSharding;
        return this;
    }

    public void setCompleteSharding(Boolean completeSharding) {
        this.completeSharding = completeSharding;
    }

    public String getMsg() {
        return this.msg;
    }

    public Msg msg(String msg) {
        this.msg = msg;
        return this;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Instant getExecuteTime() {
        return this.executeTime;
    }

    public Msg executeTime(Instant executeTime) {
        this.executeTime = executeTime;
        return this;
    }

    public void setExecuteTime(Instant executeTime) {
        this.executeTime = executeTime;
    }

    public Long getAgentId() {
        return this.agentId;
    }

    public Msg agentId(Long agentId) {
        this.agentId = agentId;
        return this;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public DdMessageType getType() {
        return this.type;
    }

    public Msg type(DdMessageType type) {
        this.type = type;
        return this;
    }

    public void setType(DdMessageType type) {
        this.type = type;
    }

    public MessageStatus getStatus() {
        return this.status;
    }

    public Msg status(MessageStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public Set<MsgTask> getMsgTasks() {
        return this.msgTasks;
    }

    public Msg msgTasks(Set<MsgTask> msgTasks) {
        this.setMsgTasks(msgTasks);
        return this;
    }

    public Msg addMsgTask(MsgTask msgTask) {
        this.msgTasks.add(msgTask);
        msgTask.setMsg(this);
        return this;
    }

    public Msg removeMsgTask(MsgTask msgTask) {
        this.msgTasks.remove(msgTask);
        msgTask.setMsg(null);
        return this;
    }

    public void setMsgTasks(Set<MsgTask> msgTasks) {
        if (this.msgTasks != null) {
            this.msgTasks.forEach(i -> i.setMsg(null));
        }
        if (msgTasks != null) {
            msgTasks.forEach(i -> i.setMsg(this));
        }
        this.msgTasks = msgTasks;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Msg)) {
            return false;
        }
        return id != null && id.equals(((Msg) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Msg{" +
            "id=" + getId() +
            ", deptIdList='" + getDeptIdList() + "'" +
            ", useridList='" + getUseridList() + "'" +
            ", toAllUser='" + getToAllUser() + "'" +
            ", progressInPercent=" + getProgressInPercent() +
            ", title='" + getTitle() + "'" +
            ", singleTitle='" + getSingleTitle() + "'" +
            ", singleUrl='" + getSingleUrl() + "'" +
            ", coverUrl='" + getCoverUrl() + "'" +
            ", numberOfShards=" + getNumberOfShards() +
            ", completeSharding='" + getCompleteSharding() + "'" +
            ", msg='" + getMsg() + "'" +
            ", executeTime='" + getExecuteTime() + "'" +
            ", agentId=" + getAgentId() +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
