package com.ratepay.bugtracker.tracker.entity

import javax.persistence.*

@Entity
@Table(name = "projects")
class Project : BaseEntity() {

    @Column(name = "name", nullable = false)
    var name : String? = null

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    var createdBy : User? = null

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var members : List<Member>? = null

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
    var bugs : List<Bug>? = null
}