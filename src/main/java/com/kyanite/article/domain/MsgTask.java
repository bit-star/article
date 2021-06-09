package com.kyanite.article.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MsgTask.
 */
@Entity
@Table(name = "msg_task")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MsgTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userid_list")
    private String useridList;

    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "rsp_msg")
    private String rspMsg;

    @Column(name = "status")
    private Integer status;

    @Column(name = "progress_in_percent")
    private Integer progressInPercent;

    @Column(name = "invalid_user_id_list")
    private String invalidUserIdList;

    @Column(name = "forbidden_user_id_list")
    private String forbiddenUserIdList;

    @Column(name = "failed_user_id_list")
    private String failedUserIdList;

    @Column(name = "read_user_id_list")
    private String readUserIdList;

    @Column(name = "unread_user_id_list")
    private String unreadUserIdList;

    @Column(name = "invalid_dept_id_list")
    private String invalidDeptIdList;

    @Column(name = "withdraw")
    private Boolean withdraw;

    @ManyToOne
    @JsonIgnoreProperties(value = { "msgTasks" }, allowSetters = true)
    private Msg msg;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MsgTask id(Long id) {
        this.id = id;
        return this;
    }

    public String getUseridList() {
        return this.useridList;
    }

    public MsgTask useridList(String useridList) {
        this.useridList = useridList;
        return this;
    }

    public void setUseridList(String useridList) {
        this.useridList = useridList;
    }

    public Long getTaskId() {
        return this.taskId;
    }

    public MsgTask taskId(Long taskId) {
        this.taskId = taskId;
        return this;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getRspMsg() {
        return this.rspMsg;
    }

    public MsgTask rspMsg(String rspMsg) {
        this.rspMsg = rspMsg;
        return this;
    }

    public void setRspMsg(String rspMsg) {
        this.rspMsg = rspMsg;
    }

    public Integer getStatus() {
        return this.status;
    }

    public MsgTask status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getProgressInPercent() {
        return this.progressInPercent;
    }

    public MsgTask progressInPercent(Integer progressInPercent) {
        this.progressInPercent = progressInPercent;
        return this;
    }

    public void setProgressInPercent(Integer progressInPercent) {
        this.progressInPercent = progressInPercent;
    }

    public String getInvalidUserIdList() {
        return this.invalidUserIdList;
    }

    public MsgTask invalidUserIdList(String invalidUserIdList) {
        this.invalidUserIdList = invalidUserIdList;
        return this;
    }

    public void setInvalidUserIdList(String invalidUserIdList) {
        this.invalidUserIdList = invalidUserIdList;
    }

    public String getForbiddenUserIdList() {
        return this.forbiddenUserIdList;
    }

    public MsgTask forbiddenUserIdList(String forbiddenUserIdList) {
        this.forbiddenUserIdList = forbiddenUserIdList;
        return this;
    }

    public void setForbiddenUserIdList(String forbiddenUserIdList) {
        this.forbiddenUserIdList = forbiddenUserIdList;
    }

    public String getFailedUserIdList() {
        return this.failedUserIdList;
    }

    public MsgTask failedUserIdList(String failedUserIdList) {
        this.failedUserIdList = failedUserIdList;
        return this;
    }

    public void setFailedUserIdList(String failedUserIdList) {
        this.failedUserIdList = failedUserIdList;
    }

    public String getReadUserIdList() {
        return this.readUserIdList;
    }

    public MsgTask readUserIdList(String readUserIdList) {
        this.readUserIdList = readUserIdList;
        return this;
    }

    public void setReadUserIdList(String readUserIdList) {
        this.readUserIdList = readUserIdList;
    }

    public String getUnreadUserIdList() {
        return this.unreadUserIdList;
    }

    public MsgTask unreadUserIdList(String unreadUserIdList) {
        this.unreadUserIdList = unreadUserIdList;
        return this;
    }

    public void setUnreadUserIdList(String unreadUserIdList) {
        this.unreadUserIdList = unreadUserIdList;
    }

    public String getInvalidDeptIdList() {
        return this.invalidDeptIdList;
    }

    public MsgTask invalidDeptIdList(String invalidDeptIdList) {
        this.invalidDeptIdList = invalidDeptIdList;
        return this;
    }

    public void setInvalidDeptIdList(String invalidDeptIdList) {
        this.invalidDeptIdList = invalidDeptIdList;
    }

    public Boolean getWithdraw() {
        return this.withdraw;
    }

    public MsgTask withdraw(Boolean withdraw) {
        this.withdraw = withdraw;
        return this;
    }

    public void setWithdraw(Boolean withdraw) {
        this.withdraw = withdraw;
    }

    public Msg getMsg() {
        return this.msg;
    }

    public MsgTask msg(Msg msg) {
        this.setMsg(msg);
        return this;
    }

    public void setMsg(Msg msg) {
        this.msg = msg;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MsgTask)) {
            return false;
        }
        return id != null && id.equals(((MsgTask) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MsgTask{" +
            "id=" + getId() +
            ", useridList='" + getUseridList() + "'" +
            ", taskId=" + getTaskId() +
            ", rspMsg='" + getRspMsg() + "'" +
            ", status=" + getStatus() +
            ", progressInPercent=" + getProgressInPercent() +
            ", invalidUserIdList='" + getInvalidUserIdList() + "'" +
            ", forbiddenUserIdList='" + getForbiddenUserIdList() + "'" +
            ", failedUserIdList='" + getFailedUserIdList() + "'" +
            ", readUserIdList='" + getReadUserIdList() + "'" +
            ", unreadUserIdList='" + getUnreadUserIdList() + "'" +
            ", invalidDeptIdList='" + getInvalidDeptIdList() + "'" +
            ", withdraw='" + getWithdraw() + "'" +
            "}";
    }
}
