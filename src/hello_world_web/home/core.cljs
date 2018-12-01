(ns hello-world-web.home.core
  (:require
    [hello-world-web.home.events]
    [hello-world-web.home.subs]
    [hello-world-web.home.view :refer [page]]
    [hello-world-web.home.db :refer [initial-db]]
    [re-frame.core :refer [dispatch]]
    [reagent.core :as r]
    [goog.dom :as dom]))

(defn init
  []
  (dispatch [:db-load initial-db])
  (r/render [page] (dom/getElement "page-body")))
