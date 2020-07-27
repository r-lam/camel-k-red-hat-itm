from('knative:channel/words')
  .log('Received: ${body}')
  .to('slack:#knative')


camel {
  components {
    slack {
      webhookUrl '{{slack.webhook.url}}'
    }
  }
}