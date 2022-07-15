package com.ratepay.bugtracker.tracker.entity

import java.time.LocalDateTime
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "bugs")
class Bug : BaseEntity() {

    @Column(name = "title")
    var title : String? = null

    @Column(name = "description")
    var description : String? = null

    @Column(name = "priority", nullable = false)
    @Enumerated(EnumType.STRING)
    var priority: Priority = Priority.LOW

    @ManyToOne
    @JoinColumn(name = "project_id")
    var project : Project? = null

    @OneToMany(mappedBy = "bug", fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
    var notes : List<Note>? = null

    @Column(name = "is_resolved")
    var isResolved : Boolean? = false

    @ManyToOne
    @JoinColumn(name = "closed_by_id")
    var closedBy : User? = null

    @Column(name = "closed_at")
    var closedAt : ZonedDateTime? = null

    @ManyToOne
    @JoinColumn(name = "reopened_by_id")
    var reopenedBy : User? = null

    @Column(name = "reopened_at")
    var reopenedAT : LocalDateTime? = null

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    var createdBy : User? = null

    @ManyToOne
    @JoinColumn(name = "update_by_id")
    var updateBy : User? = null
}


enum class Priority {
    LOW,
    MEDIUM,
    HIGH
}