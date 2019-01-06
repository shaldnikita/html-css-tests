package ru.shaldnikita.testing.domain.entities

import java.time.LocalDateTime

case class Student(id: Long = 0,
                   personalId: Long = 0,
                   createdDate: LocalDateTime,
                   updatedDate: LocalDateTime,
                   firstName: String,
                   secondName: String,
                   results: List[QuestionResult],
                   lastLogin: LocalDateTime) extends BaseEntity
