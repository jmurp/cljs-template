(ns hello-world-web.util
  (:require
    [goog.net.XhrIo :as xhrio]))

(defn to-json-string
  "Convert clojure data to a JSON string."
  [clj-data]
  (.stringify js/JSON (clj->js clj-data)))

(defn to-clj
  "Convert a JSON string to clojure data."
  [json]
  (js->clj (.parse js/JSON json) :keywordize-keys true))

(defn apply-callback
  [success error evt]
  (let [xhr (.-target evt)]
    (if (= 200 (.getStatus xhr))
      (success evt)
      (error evt))))

(defn xhr-post
  "Make a POST request. Sends to the uri and performs the callback.
  Data sent is converted from clojure data to a JSON string."
  [uri success error clj-map]
  (xhrio/send
    uri
    (partial apply-callback success error)
    "POST"
    (to-json-string clj-map)
    {"content-type" "application/json"}))

(defn xhr-get
  "Make a GET request. Sends to the uri and performs the callback."
  [uri success error]
  (xhrio/send
    uri
    (partial apply-callback success error)))

(defn xhr-response
  "Get the response from an event attached to an XhrIo instance or from
  the static XhrIo call. Response is converted to clojure data."
  [evt]
  (to-clj (.getResponseText (.-target evt))))
