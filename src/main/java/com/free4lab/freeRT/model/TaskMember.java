package com.free4lab.freeRT.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "task_member")
public class TaskMember implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name = "userid", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name = "taskid")
    private Task task;

    @Column(name = "identity",nullable = false)
    private Integer identity;

    public TaskMember(){}

    public TaskMember(User user, Task task, Integer identity) {
        this.user = user;
        this.task = task;
        this.identity = identity;
    }

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}
    public Task getTask() {return task;}
    public void setTask(Task task) {this.task = task;}
    public Integer getIdentity() {return identity;}
    public void setIdentity(Integer identity) {this.identity = identity;}
}
