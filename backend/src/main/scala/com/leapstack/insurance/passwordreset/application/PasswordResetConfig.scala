package com.leapstack.insurance.passwordreset.application

import com.leapstack.insurance.common.ConfigWithDefault
import com.typesafe.config.Config

trait PasswordResetConfig extends ConfigWithDefault {
  def rootConfig: Config

  lazy val resetLinkPattern =
    getString("bootzooka.reset-link-pattern", "http://localhost:8080/#/password-reset?code=%s")
}
