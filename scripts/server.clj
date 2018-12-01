;;require the jetty server and handler
(require '[hello-world-server.core :refer [handler]])
(require '[ring.adapter.jetty :refer [run-jetty]])
;;start the server with join false in order to stop from blocking the thread
;;which allows fig to open up the brepl
(run-jetty handler {:port 4000 :join? false})
