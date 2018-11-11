(require '[ring.adapter.jetty :refer [run-jetty]])
(require '[ring.middleware.defaults :refer [wrap-defaults site-defaults]])
(require '[ring.util.response :refer [resource-response content-type]])

(defn handler [req]
  (or
    (when (= "/" (:uri req))
     (some->
       (resource-response "index.html" {:root "public"})
       (content-type "text/html; charset=utf-8")))
    (when (= "/test" (:uri req))
      (some->
        (resource-response "test.html" {:root "public"})
        (content-type "text/html; charset=utf-8")))
    {:status 404
     :headers {"Content-type" "text/html"}
     :body "Not found"}))

(run-jetty
  (wrap-defaults handler site-defaults)
  {:port 4000 :join? false})
