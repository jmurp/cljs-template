(ns hello-world-server.core
  (:require
    [hello-world-server.api :refer [api-routes]]
    [hello-world-server.util :refer [persist-session]]
    [hello-world-server.middleware :refer [wrap-common-middleware wrap-site-middleware wrap-api-middleware]]
    [ring.util.response :refer [resource-response content-type response]]
    [ring.util.request :refer [in-context?]]))

(defn return-site
  "Returns an appropriate site resource reponse or nil if the there is an error."
  [filename cookies]
  (some->
    (resource-response filename {:root "public"})
    (content-type "text/html; charset=utf-8")
    (assoc :cookies (persist-session cookies))))

(defn return-styles
  "Returns the appropriate css stylessheet."
  [filename cookies]
  (some->
    (resource-response filename {:root "public"})
    (content-type "text/css; charset=utf-8")
    (assoc :cookies (persist-session cookies))))

(defn return-script
  "Returns the appropriate js file."
  [filename cookies]
  (some->
    (resource-response filename {:root "public"})
    (content-type "application/javascript; charset=utf-8")
    (assoc :cookies (persist-session cookies))))

(defn return-image
  "Returns the appropriate image file."
  [filename cookies]
  (some->
    (resource-response filename {:root "public"})
    (content-type "image/jpeg")
    (assoc :cookies (persist-session cookies))))

(defn site-routes
  "Routes to handle site requests. Unmatched uri's are redirected to the homepage.
  Keeps session alive by associating the same cookies map in the response."
  [req]
  (let [{cookies :cookies uri :uri} req]
    (if (in-context? req "/cljs-out/")
      (return-script uri cookies)
      (if (in-context? req "/images/")
        (return-image uri cookies)
        (case uri
           "/" (return-site "index.html" cookies)
           "/css/styles.css" (return-styles "/css/styles.css" cookies)
           {:status 404 :cookies (persist-session cookies)})))))

(defn route-handler
  "Define the routes of this handler, applying different middleware for api and site requests."
  [req]
  (if (.startsWith (:uri req) "/api")
      ((wrap-api-middleware api-routes) req)
      ((wrap-site-middleware site-routes) req)))

(defn handler
  "Define the highest handler. Wrap middleware common to all requests."
  [req]
  ((wrap-common-middleware route-handler) req))
