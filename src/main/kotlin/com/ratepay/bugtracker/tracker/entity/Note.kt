package com.ratepay.bugtracker.tracker.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "notes")
class Note : BaseEntity() {

    @Column(name = "body")
    var body : String? = null

    @ManyToOne
    @JoinColumn(name = "bug_id")
    var bug : Bug? = null

    @ManyToOne
    @JoinColumn(name = "author_id")
    var author : User? = null

}