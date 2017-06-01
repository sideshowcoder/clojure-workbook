(ns clojure-workbook.chapter-4)

(def filename "suspects.csv")

(defn str->int [s] (Integer. s))

(def conversions {:name identity :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  [s]
  (map #(clojure.string/split % #",")
       (clojure.string/split-lines s)))

(def vamp-keys [:name :glitter-index])

(defn mapify
  [rows]
  (map mapify-row rows))

(defn glitter-filter
  [min-glitter records]
  (filter #(>= (:glitter-index %) min-glitter) records))


(defn mapify-row
  [row]
  (into (sorted-map)
        (map (fn [[vamp-key value]] [vamp-key (convert vamp-key value)])
             (map vector vamp-keys row))))

(defn serialize
  [suspect]
  (clojure.string/join "," [(str (:name suspect)) (str (:glitter-index suspect))]))

(defn append
  [s]
  (clojure.string/join
   "\n"
   (conj (clojure.string/split-lines (file-contents)) s)))

(defn append-to-file
  [suspect]
  (when (validate vamp-keys suspect)
    (spit filename (append (serialize suspect)))
    't))

(append-to-file {:name "Blade" :glitter-index 2})
(append-to-file {:name "foo" :glitter-index "bar"})

(defn file-contents
  []
  (slurp filename)))

(not (clojure.string/blank? "backend"))

(def validations
  {:name (fn [s] (not (clojure.string/blank? s)))
   :glitter-index (fn [i] (number? i))})

(defn validate
  [keys record]
  (every? (fn [[key value]] ((get validations key) value)) record))

(map #(:name %) (glitter-filter 2 (mapify (parse (file-contents)))))
