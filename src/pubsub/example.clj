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
