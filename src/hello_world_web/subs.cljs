(ns hello-world-web.subs
  (:require
    [re-frame.core :refer [reg-sub subscribe]]))

;;SUB to the view of the app
(reg-sub
  :view
  (fn [db _]
    (:view db)))
