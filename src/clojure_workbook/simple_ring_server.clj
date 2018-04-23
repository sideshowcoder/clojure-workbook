(ns clojure-workbook.simple-ring-server
  (:require [ring.adapter.jetty :refer [run-jetty]])
  (:gen-class))

(defn handler
  [req]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hello Ring!"})

(defn -main
  [& args]
  (let [port (Integer/valueOf (or (System/getenv "PORT") "3000"))]
    (run-jetty handler {:port port})))

