package com.ratepay.bugtracker.tracker.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Index
import javax.persistence.Table

@Entity
@Table(name = "users",
    indexes = [
        Index(name = "users_user_name_idx", columnList = "user_name")]
)

class User : BaseEntity() {
    @Column(name = "user_name", nullable = false)
    var userName : String? = null
    @Column(name = "user_password", nullable = false)
    var userPassword : String? = null
}