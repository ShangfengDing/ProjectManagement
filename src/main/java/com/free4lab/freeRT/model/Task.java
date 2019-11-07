package com.free4lab.freeRT.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table (name="task")
public class Task implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "taskid", unique = true, nullable = false)
    private Integer taskid;

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinColumn(name = "projectid")
    private Project project;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "state" , nullable = false)
    private Integer state;

    @Column(name = "amount" ,nullable = false)
    private Integer amount;

    @Column(name = "time" ,  updatable = false)
    private Timestamp time;

    @Column(name = "priority" ,nullable = false)
    private Integer priority;

    @Column(name = "finishtime" )
    private Timestamp finishtime;

    @Column(name = "taskPic")
    private String taskPic;

    @OneToMany(mappedBy = "task",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="taskid")
    private Collection<TaskMember> taskMembers;

    @Column(name="plan_beginTime")
    private Timestamp plan_beginTime;

    @Column(name="plan_endTime")
    private Timestamp plan_endTime;

    @Column(name="real_beginTime")
    private Timestamp real_beginTime;

    @Column(name="real_endTime")
    private Timestamp real_endTime;

    public Task() {

    }

    public Task(Project project, String description, Integer state, Integer amount, Timestamp time, Integer priority,Timestamp finishtime) {
        this.project = project;
        this.description = description;
        this.state = state;
        this.amount = amount;
        this.time = time;
        this.priority = priority;
        this.finishtime = finishtime;
        this.taskPic = taskPic;
    }

    public Integer getTaskid() { return taskid;}
    public void setTaskid(Integer taskid) { this.taskid = taskid;}

    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
    }

    public String getDescription() { return description;}
    public void setDescription(String description) { this.description = description;}

    public Integer getState() { return state;}
    public void setState(Integer state) { this.state = state;}

    public Integer getAmount() { return amount;}
    public void setAmount(Integer amount) {this.amount = amount;}

    public Timestamp getTime() { return time;}
    public void setTime(Timestamp time) { this.time = time;}

    public Integer getPriority() { return priority;}
    public void setPriority(Integer priority) { this.priority = priority;}

    public Timestamp getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(Timestamp finishtime) {
        this.finishtime = finishtime;
    }

    public Collection<TaskMember> getTaskMembers() {return taskMembers==null?new HashSet<TaskMember>():taskMembers;}
    public void setTaskMembers(Collection<TaskMember> taskMembers) {this.taskMembers = taskMembers;}

    public String getTaskPic() {
        return taskPic;
    }

    public void setTaskPic(String taskPic) {
        this.taskPic = taskPic;
    }

    public Timestamp getPlan_beginTime() {
        return plan_beginTime;
    }

    public void setPlan_beginTime(Timestamp plan_beginTime) {
        this.plan_beginTime = plan_beginTime;
    }

    public Timestamp getPlan_endTime() {
        return plan_endTime;
    }

    public void setPlan_endTime(Timestamp plan_endTime) {
        this.plan_endTime = plan_endTime;
    }

    public Timestamp getReal_beginTime() {
        return real_beginTime;
    }

    public void setReal_beginTime(Timestamp real_beginTime) {
        this.real_beginTime = real_beginTime;
    }

    public Timestamp getReal_endTime() {
        return real_endTime;
    }

    public void setReal_endTime(Timestamp real_endTime) {
        this.real_endTime = real_endTime;
    }
}
