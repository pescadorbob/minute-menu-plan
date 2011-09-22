environments {
  development {
    asynchronous.mail.default.attempt.interval = 300000l       // Five minutes
    asynchronous.mail.default.max.attempts.count = 1
    asynchronous.mail.send.repeat.interval = 300000l           // Five minute
    asynchronous.mail.expired.collector.repeat.interval = 607000l
    asynchronous.mail.messages.at.once = 100
    asynchronous.mail.send.immediately = true
  }
  production {
    asynchronous.mail.default.attempt.interval = 300000l       // Five minutes
    asynchronous.mail.default.max.attempts.count = 10
    asynchronous.mail.send.repeat.interval = 60000l           // One minute
    asynchronous.mail.expired.collector.repeat.interval = 607000l
    asynchronous.mail.messages.at.once = 100
    asynchronous.mail.send.immediately = true
  }
  test {
    asynchronous.mail.default.attempt.interval = 300000l       // Five minutes
    asynchronous.mail.default.max.attempts.count = 1
    asynchronous.mail.send.repeat.interval = 60000l           // One minute
    asynchronous.mail.expired.collector.repeat.interval = 607000l
    asynchronous.mail.messages.at.once = 100
    asynchronous.mail.send.immediately = true
  }
}