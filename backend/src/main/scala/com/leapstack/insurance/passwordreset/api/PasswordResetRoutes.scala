package com.leapstack.insurance.passwordreset.api

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import com.leapstack.insurance.common.api.RoutesSupport
import com.leapstack.insurance.passwordreset.application.PasswordResetService
import com.leapstack.insurance.user.api.SessionSupport
import io.circe.generic.auto._

trait PasswordResetRoutes extends RoutesSupport with SessionSupport {

  def passwordResetService: PasswordResetService

  val passwordResetRoutes = pathPrefix("passwordreset") {
    post {
      path(Segment) { code =>
        entity(as[PasswordResetInput]) { in =>
          onSuccess(passwordResetService.performPasswordReset(code, in.password)) {
            case Left(e) => complete(StatusCodes.Forbidden, e)
            case _       => completeOk
          }
        }
      } ~ entity(as[ForgotPasswordInput]) { in =>
        onSuccess(passwordResetService.sendResetCodeToUser(in.login)) {
          complete("success")
        }
      }
    }
  }
}

case class PasswordResetInput(password: String)

case class ForgotPasswordInput(login: String)
