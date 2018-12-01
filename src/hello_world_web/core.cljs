(ns hello-world-web.core
  (:require
    [hello-world-web.home.core :as home]
    [goog.events :as events]
    [goog.dom :as dom]))

(defn set-load-event
  "Sets the js/window load event to the provided function."
  [init]
  (events/listen js/window "load" init))

;;set the onload event based on the title content of the webpage
(let [title (.-innerHTML (dom/getElement "page-title"))]
  (case title
    "Home" (set-load-event (fn [] (home/init)))))
