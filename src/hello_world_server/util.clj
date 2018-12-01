(ns hello-world-server.util
  (:require
    [crypto.random])
  (:import
    [java.text SimpleDateFormat]
    [java.util Date TimeZone]))

(defn gen-session-hash
  "Generates a random eight byte hash to use as a session hash."
  []
  (crypto.random/hex 8))

(defn persist-session
  "If no user hash is present, generate one. Otherwise return the original map."
  [cookies]
  (if (get cookies "session-hash")
    cookies
    (assoc cookies "session-hash" {:value (gen-session-hash) :max-age 3600})))


(defn create-timestamp
  "Creates an RFC 3339 timestamp (UTC)."
  []
  (let [fmat (SimpleDateFormat. "yyyy-MM-dd'T'HH:mm:ss.SSS")]
    (.setTimeZone fmat (TimeZone/getTimeZone "GMT"))
    (str (.format fmat (Date.)) "Z")))
