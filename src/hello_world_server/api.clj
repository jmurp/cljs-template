(ns hello-world-server.api
  (:require
    [hello-world-server.util :refer [persist-session create-timestamp]]))

(defn test-get-fn
  "A function to place test get api responses."
  [req]
  (let [{cookies :cookies} req]
    (-> {:body {}}
      (update :body assoc :test true)
      (assoc :cookies (persist-session cookies)))))

(defn test-post-fn
  "A function to place test post api responses."
  [req]
  (let [{cookies :cookies params :params} req]
    (-> {:body {}}
      (update :body assoc :test true)
      (assoc :cookies (persist-session cookies)))))

(defn api-routes
  "Define the routes for this api, matching by case."
  [req]
  (case (:uri req)
    "/api/test/get" (test-get-fn req)
    "/api/test/post" (test-post-fn req)
    nil))
