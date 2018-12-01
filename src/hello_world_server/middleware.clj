(ns hello-world-server.middleware
  (:require
    [ring.middleware.x-headers :as x]
    [ring.middleware.keyword-params :refer [wrap-keyword-params]]
    [ring.middleware.nested-params :refer [wrap-nested-params]]
    [ring.middleware.anti-forgery :refer [wrap-anti-forgery]]
    [ring.middleware.multipart-params :refer [wrap-multipart-params]]
    [ring.middleware.params :refer [wrap-params]]
    [ring.middleware.cookies :refer [wrap-cookies]]
    [ring.middleware.not-modified :refer [wrap-not-modified]]
    [ring.middleware.absolute-redirects :refer [wrap-absolute-redirects]]
    [ring.middleware.ssl :refer [wrap-ssl-redirect wrap-hsts wrap-forwarded-scheme]]
    [ring.middleware.proxy-headers :refer [wrap-forwarded-remote-addr]]
    [ring.middleware.json :refer [wrap-json-response wrap-json-params]]))


(def security-config
  {
    :anti-forgery true
    :xss-protection {:enable? true :mode :block}
    :frame-options :sameorigin
    :content-type-options :nosniff
    :hsts false;;production only
    :ssl-redirect false;;production only
    :proxy false});;production only

(defn- wrap [handler middleware options]
  "Helper function."
  (if (true? options)
    (middleware handler)
    (if options
      (middleware handler options)
      handler)))

(defn- wrap-xss-protection [handler options]
  "Wrap headers which help browswer protect against cross origin attacks."
  (x/wrap-xss-protection handler (:enable? options true) (dissoc options :enable?)))

(defn- wrap-x-headers [handler options]
  "Wrap all the relevant x-headers."
  (-> handler
      (wrap wrap-xss-protection         (:xss-protection options false))
      (wrap x/wrap-frame-options        (:frame-options options false))
      (wrap x/wrap-content-type-options (:content-type-options options false))))

(defn wrap-common-middleware
  "Wraps middleware used by both the site routes and api routes.
  This is a subset of the ring site-defaults."
  [handler]
  (-> handler
    (wrap wrap-keyword-params true)
    (wrap wrap-nested-params true)
    (wrap wrap-multipart-params true)
    (wrap wrap-params true)
    (wrap wrap-cookies true)
    (wrap wrap-absolute-redirects true)
    (wrap wrap-x-headers security-config)
    (wrap wrap-hsts (get security-config :hsts false))
    (wrap wrap-ssl-redirect (get security-config :ssl-redirect false))
    (wrap wrap-forwarded-scheme (get security-config :proxy false))
    (wrap wrap-forwarded-remote-addr (get security-config :proxy false))))


(defn wrap-site-middleware
  "Wrap middleware pertaining to the site routes but not the api routes."
  [handler]
  (-> handler
    (wrap wrap-anti-forgery (get security-config :anti-forgery))
    (wrap wrap-not-modified true)))

(defn wrap-api-middleware
  "Wrap middleware pertaining to the api routes but not the site routes."
  [handler]
  (-> handler
    (wrap-keyword-params)
    (wrap-json-params)
    (wrap-json-response)))
