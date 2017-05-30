(ns clojure-workbook.chapter-9)

(def google-query-string "https://www.google.co.uk/?q=")
(def bing-query-string "http://www.bing.com/search?q=")

(defn make-query
  [query url]
  (if (= url google-query-string)
    (Thread/sleep 10000))
  (slurp (str url query)))

(defn http-query
  [s]
  (let [query-promise (promise)]
    (doseq [url [google-query-string bing-query-string]]
      (future (if-let [response (make-query s url)]
                (deliver query-promise response))))
    @query-promise))
