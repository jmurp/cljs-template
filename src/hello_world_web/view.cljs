(ns hello-world-web.view
  (:require
    [hello-world-web.styles :refer [styles]]
    [re-frame.core :refer [subscribe dispatch]]
    [reagent.core :as r]))


(defn merge-styles
  [skeys]
  (reduce
    (fn [m arg]
      (if (vector? arg)
        (let [kw (get arg 0)
              args (into [] (rest arg))]
          (merge m (apply (get styles kw) args)))
        (merge m (get styles arg))))
    {}
    skeys))

(defn elt
  [styles tag-name evt-map content]
  [tag-name (assoc evt-map :style (merge-styles styles)) content])

(defn home []
  (elt [:hello [:border "13px"]]
       :div {} "Hellowww"))


(defn page []
  (let [page-view (subscribe [:view])]
    (fn []
      [:div#page-container
       (case @page-view
         "home" [home]
        [home])])))
