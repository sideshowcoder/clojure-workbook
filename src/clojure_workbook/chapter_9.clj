(ns clojure-workbook.chapter-9
  (:require [net.cgrand.enlive-html :as html]))

(defn extract-results-bing
  [html-str]
  (map #(get-in % [:attrs :href])
       (html/select (html/html-snippet html-str) [:ol#b_results :h2 :a])))

(def search-engines
  {:bing {:search-url "http://www.bing.com/search?q="
          :extract-results extract-results-bing}})

(defn make-query
  [query url]
  (slurp (str url query)))

(defn http-query
  [engines s]
  (let [query-promise (promise)]
    (doseq [engine engines]
      (let [url (get-in search-engines [engine :search-url])]
        (future (if-let [response [engine (make-query s url)]]
                  (deliver query-promise response)))))
    @query-promise))

(defn search-links
  [engines s]
  (let [[engine result] (http-query engines s)]
    ((get-in search-engines [engine :extract-results]) result)))
