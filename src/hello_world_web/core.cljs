(ns hello-world-web.core
  (:require
    [hello-world-web.events]
    [hello-world-web.subs]
    [hello-world-web.view :refer [page]]
    [hello-world-web.db :refer [initial-db]]
    [re-frame.core :as rf]
    [reagent.core :as r]
    [goog.events :as events]
    [goog.dom :as dom]))

(events/listen js/window "load"
  (fn []
    (rf/dispatch [:db-load initial-db])
    (r/render [page] (dom/getElement "app"))))
