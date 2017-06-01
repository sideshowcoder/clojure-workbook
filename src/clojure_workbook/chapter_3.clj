(ns clojure-workbook.chapter-3)

(defn add-100
  [number]
  (+ number 100))

(defn dec-maker
  [by]
  #(- % by))

(def dec-10 (dec-maker 10))

(dec-10 100)

(map inc [1 2 3])

(defn mapset
  [f coll] (set (map f coll)))

(mapset inc [1 2 3])

(def asym-alien-parts [{:name "head" :size 1}
                       {:name "1-eye" :size 1}
                       {:name "1-leg" :size 10}])

(defn matching-parts
  ;; get a part an return seq of the matching parts
  [part n]
  (let [prefixes (map #(str % "-") (range 1 (inc n)))] ;; range
    (map (fn [prefix] {:name (clojure.string/replace-first (:name part) "1-" prefix) :size (:size part)})
         prefixes)))

(defn sym-parts
  [asym-parts n]
  (reduce (fn [sym-parts part] (into sym-parts (conj (set (matching-parts part n)) part)))
          []
          asym-parts))

(sym-parts asym-alien-parts 2)
