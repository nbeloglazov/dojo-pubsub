dojo-pubsub
===========

PubSub mini-library developed at July Clojure Dojo at uSwitch.

Library provides 2 functions:

* `(send-it message)` - sends message, message is any clojure object.
* `(subscribe-with handler)`, `(subscribe-with predicate handler)` - subscribes handler. Predicate filters all messages, so only suitable are processed.

`subscribe-with` returns a promise object. If is used to unsubscribe handler: just deliver anything to the promise and handler will be unsubscribed.
Library uses multicast IP address so computers on the same network can communicate without knowing about each other. Basically it is a translation of an example from javadoc to clojure: http://download.java.net/jdk7/archive/b123/docs/api/java/net/MulticastSocket.html

Usage:
```clojure
(ns pubsub.example
  (:require [pubsub.core :refer [send-it subscribe-with]]))

; subscribe to all messages
(subscribe-with #(println "Simplest subscribe" %))

; send message, we should see it printed in console
(send-it "something")

; subscripe only to messages which are maps and contain :topic == :dojo
(subscribe-with #(= (:topic %) :dojo)
                #(println "Dojo message" %))

; subscribe only to messages with topic :work
(subscribe-with #(= (:topic %) :work)
                #(println "Work message" %))

; subscript only to messages with topic :home
(subscribe-with #(= (:topic %) :home)
                #(println "Home message" %))

; send messages with different topics
(send-it {:topic :dojo :message "PubSub!"})
(send-it {:topic :work :message "You're at work..."})
(send-it {:topic :home :message "You're at home..."})
```
