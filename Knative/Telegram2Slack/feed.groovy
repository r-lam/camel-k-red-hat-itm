from('telegram:bots?authorizationToken={{telegram.bot.token}}')
  .convertBodyTo(String.class)
  .to('log:info')
  .to('knative:channel/messages')