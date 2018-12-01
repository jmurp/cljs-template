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

(defn xhr-post
  "Make a POST request. Sends to the uri and performs the callback.
  Data sent is converted from clojure data to a JSON string."
  [uri callback clj-map]
  (xhrio/send
    uri
    callback
    "POST"
    (to-json-string clj-map)
    {"content-type" "application/json"}))

(defn xhr-get
  "Make a GET request. Sends to the uri and performs the callback."
  [uri callback]
  (xhrio/send
    uri
    callback))

(defn xhr-response
  "Get the response from an event attached to an XhrIo instance or from
  the static XhrIo call. Response is converted to clojure data."
  [evt]
  (let [response-as-str (.getResponseText (.-target evt))]
    (to-clj response-as-str)))
