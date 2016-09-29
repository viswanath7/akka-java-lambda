package com.example.akka.actors;


import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;

/**
 * Greeter actor that processes a 'GREET' message it receives and
 * returns the sender a DONE message
 */
public class Greeter extends AbstractActor {

  private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

  public enum Message {
    GREET, DONE
  }

  public Greeter() {
    receive(
        ReceiveBuilder.matchEquals(Message.GREET, m -> {
          log.debug("Greeter Actor: Received GREET message");
          System.out.println("Hello World!");
          log.debug("Greeter Actor: Sending DONE message");
          sender().tell(Message.DONE, self());
        }).build());
  }
}

