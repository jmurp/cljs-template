(ns hello-world-web.events
  (:require
    [hello-world-web.util :refer [xhr-get xhr-post xhr-response]]
    [re-frame.core :refer [reg-event-fx reg-event-db reg-fx dispatch]]))

;;HELPER FXNS

;;...

;;REGISTERED FX

;;FX to print the database
(reg-fx
  :print
  (fn [{:keys [access-fn db]}]
    (println (apply access-fn [db]))))

;;FX for making a GET request to an API
(reg-fx
  :api-get
  (fn [{:keys [uri callback]}]
    (xhr-get uri callback)))

;;FX for making a POST request to an API
(reg-fx
  :api-post
  (fn [{:keys [uri callback data]}]
    (xhr-post uri callback data)))

;;...

;;REGISTERED EVENT HANDLERS

;;HANDLER for printing something from the database
(reg-event-fx
  :print-db
  (fn [cofx [_ access-fn]]
    {:print {:db (:db cofx)
             :access-fn access-fn}}))

;;HANDLER for calling the api test get endpoint
(reg-event-fx
  :test-api-get
  (fn [cofx [_]]
    {:api-get
      {:uri "/api/test/get"
       :callback (fn [e] (println (xhr-response e)))}}))

;;HANDLER for calling the api test post endpoint
(reg-event-fx
  :test-api-post
  (fn [cofx [_]]
    {:api-post
      {:uri "/api/test/post"
       :callback (fn [e] (println (xhr-response e)))
       :data {}}}))

;;HANDLER for loading the initial database state
(reg-event-fx
  :db-load
  (fn [cofx [_ initial-db]]
     {:db initial-db}))

;;HANDLER for changing the view of the page (either store, modal, or cart)
(reg-event-db
  :view-change
  (fn [db [_ view]]
    (assoc db :view view)))

    ;;...
