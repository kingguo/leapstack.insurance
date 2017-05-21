package com.leapstack.insurance

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import com.leapstack.insurance.common.api.RoutesRequestWrapper
import com.leapstack.insurance.passwordreset.api.PasswordResetRoutes
import com.leapstack.insurance.swagger.SwaggerDocService
import com.leapstack.insurance.user.api.UsersRoutes
import com.leapstack.insurance.version.VersionRoutes

trait Routes extends RoutesRequestWrapper with UsersRoutes with PasswordResetRoutes with VersionRoutes {

  def system: ActorSystem
  def config: ServerConfig

  lazy val routes = requestWrapper {
    pathPrefix("api") {
      passwordResetRoutes ~
        usersRoutes ~
        versionRoutes
    } ~
      getFromResourceDirectory("webapp") ~
      new SwaggerDocService(config.serverHost, config.serverPort, system).routes ~
      path("") {
        getFromResource("webapp/index.html")
      }
  }
}
