(ns hello-world-web.home.view
  (:require
    [re-frame.core :refer [subscribe dispatch]]
    [reagent.core :as r]))

(defn home []
  [:h1 "hellowww"])

(defn page []
  (let [page-view (subscribe [:view])]
    (fn []
      [:div#page-container
       (case @page-view
         "home" [home]
        [home])])))
