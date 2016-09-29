package com.example.akka.actors;


import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;

public class Commencer extends AbstractActor {

  private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

  public Commencer() {
    receive(
        ReceiveBuilder.matchEquals(Greeter.Message.DONE, m -> {
          log.debug("Commencer Actor: Received DONE message");
          log.debug("Commencer Actor: Stopping actor Commencer and the application along with it ...");
          context().stop(self());
        }).build());
  }

  @Override
  public void preStart() {
    log.debug("Commencer Actor: Inside pre-start hook ...");
    log.debug("Commencer Actor: Creating Greeter Actor");
    final ActorRef greeter = getContext().actorOf(Props.create(Greeter.class), "greeter");
    log.debug("Commencer Actor: Send GREET message to Greeter Actor to perform the greeting ...");
    greeter.tell(Greeter.Message.GREET, self());
    log.debug("Commencer Actor: Exiting pre-start hook ...");
  }
}

