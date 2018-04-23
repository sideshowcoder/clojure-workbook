(defproject clojure-workbook "0.1.0-SNAPSHOT"
  :description "Excercises from http://www.braveclojure.com"
  :url "https://github.com/sideshowcoder/clojure-workbook"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main clojure-workbook.simple-ring-server
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [enlive "1.1.6"]
                 [ring/ring-core "1.6.3"]
                 [ring/ring-jetty-adapter "1.6.3"]
                 [org.clojure/core.async "0.3.443"]]
  :profiles {:uberjar {:aot :all
                       :main clojure-workbook.simple-ring-server}})
