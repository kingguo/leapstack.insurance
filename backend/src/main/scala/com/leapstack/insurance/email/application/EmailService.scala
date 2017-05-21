package com.leapstack.insurance.email.application

import com.leapstack.insurance.email.domain.EmailContentWithSubject

import scala.concurrent.Future

trait EmailService {

  def scheduleEmail(address: String, emailData: EmailContentWithSubject): Future[Unit]

}
