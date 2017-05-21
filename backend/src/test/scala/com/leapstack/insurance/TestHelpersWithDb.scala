package com.leapstack.insurance

import com.leapstack.insurance.common.sql.SqlDatabase
import com.leapstack.insurance.email.application.{DummyEmailService, EmailTemplatingEngine}
import com.leapstack.insurance.user.application.{UserDao, UserService}
import com.leapstack.insurance.user.domain.User
import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.ExecutionContext

trait TestHelpersWithDb extends TestHelpers with ScalaFutures {

  lazy val emailService          = new DummyEmailService()
  lazy val emailTemplatingEngine = new EmailTemplatingEngine
  lazy val userDao               = new UserDao(sqlDatabase)
  lazy val userService           = new UserService(userDao, emailService, emailTemplatingEngine)

  def sqlDatabase: SqlDatabase

  implicit lazy val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  def newRandomStoredUser(password: Option[String] = None): User = {
    val u = newRandomUser(password)
    userDao.add(u).futureValue
    u
  }
}
