(ns clojure-workbook.chapter-10
  (:require [clojure.string :as str]))

(let [a (atom 0)]
  (dotimes [_ 10]
    (swap! a inc))
  @a)

(def quote-url "http://www.braveclojure.com/random-quote")

(defn normalized-words
  [sentence]
  (->> (str/split sentence #" ")
       (map #(str/replace %1 #"\.|\?|!|--" ""))
       (map str/trim)
       (filter not-empty)
       (map str/lower-case)))

(defn word-count
  [s]
  (let [words (normalized-words s)]
    (reduce-kv #(assoc %1 %2 (count %3)) {}
               (group-by identity words))))

(defn aggregate-word-count
  [count-map aggregate]
  (reduce-kv
   (fn [_ k v]
     (swap! aggregate (fn [c] (update-in c [k] #(+ (if (nil? %1) 0 %1) v)))))
   nil
   count-map))

(defn quote-word-count
  [n]
  (let [urls (replicate n quote-url)
        count (atom {})]
    (last (pmap
           (fn [url] (aggregate-word-count (word-count (slurp url)) count))
           urls))))

(time (quote-word-count 100))


;; Model transaction between characters, applying a potion to another character
(def warrior (ref {:name "Gimli"
                   :current-health 15
                   :max-health 40}))

(def healer (ref {:name "Karl"
                  :inventory #{{:name "potion" :hp 10}}}))


(defn heal
  [healer character]
  (dosync
   (when-let [potion (some #(if (= (:name %) "potion") %) (:inventory @healer))]
     (alter healer update-in [:inventory] disj potion)
     (alter character update-in [:current-health] #(+ % (:hp potion))))))


(heal healer warrior)
