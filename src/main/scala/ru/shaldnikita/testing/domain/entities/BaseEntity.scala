package ru.shaldnikita.testing.domain.entities

import java.time.LocalDateTime

abstract class BaseEntity {
  def id: Long

  def personalId: Long

  def createdDate: LocalDateTime

  def updatedDate: LocalDateTime
}


