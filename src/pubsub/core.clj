(ns pubsub.core
  (:import [java.net InetAddress DatagramPacket MulticastSocket]))

(defn get-packet [message group port]
  (DatagramPacket. (.getBytes message) (.length message) group port))

(def address "228.5.6.7")
(def port 6789)
(def group  (InetAddress/getByName address))

(defn init-comm []
  (let [s (MulticastSocket. port)]
    (.joinGroup s group)
    s))

(def socket (init-comm))

(defn send-it "Send whatever"
  [message]
  (.send socket (get-packet (pr-str message) group port)))

(defn process-message [socket predicate handler finished]
  (let [size 1000
        message (byte-array size)
        packet (DatagramPacket. message size)]
    (when-not (realized? finished)
      (.receive socket packet)
      (let [obj (-> packet .getData (String.) read-string)]
        (when (predicate obj)
          (handler obj))))
      (recur socket predicate handler finished)))

(defn subscribe-with
  ([handler]
   (subscribe-with (constantly true) handler))
  ([predicate handler]
   (let [socket (init-comm)
         finished (promise)]
     (future
       (process-message socket predicate handler finished)
       (.leaveGroup socket group))
     finished)))
