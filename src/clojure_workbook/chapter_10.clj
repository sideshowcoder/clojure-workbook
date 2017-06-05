(ns clojure-workbook.chapter-10
  (:require [clojure.string :as str]))

(let [a (atom 0)]
  (dotimes [_ 10]
    (swap! a inc))
  @a)


(defn get-quote-promise
  []
  (let [quote-promise (promise)]
    (future (deliver quote-promise (slurp "http://www.braveclojure.com/random-quote")))
    quote-promise))

(defn word-count
  [s]
  (let [words (->> (str/split s #" ")
                   (map #(str/replace %1 #"\.|\?|!|--" ""))
                   (map str/trim)
                   (filter not-empty)
                   (map str/lower-case))]
    (reduce-kv #(assoc %1 %2 (count %3)) {}
               (group-by identity words))))

(defn aggregate-word-count
  [count-map aggregate]
  (reduce-kv
   (fn [_ k v]
     (swap! aggregate (fn [c] (update-in c [k] #(+ (if (nil? %1) 0 %1) v)))))
   nil
   count-map))

(aggregate-word-count (word-count "foo foo bar") word-counter)

(defn process-quotes
  [f count]
  (let [quote-promises (map (fn [_] (get-quote-promise)) (range count))]
    ;; parallelizing here is probably not very useful actually as aggregating
    ;; such a small amount of data in a reduce is almost instant this means the
    ;; overhead of pmap is dominating
    (pmap (fn [quote-promise] (f @quote-promise)) quote-promises)))


(defn quote-word-count
  [n]
  (let [counter (atom {})]
    (doall (process-quotes (fn [quote] (aggregate-word-count (word-count quote) counter)) n))
    @counter))

(time (quote-word-count 100))
