(ns clojure-workbook.chapter-5
  (:gen-class))

(def character
  {:name "Phil"
   :attributes {:intelligence 3
                 :dexterity 2
                :strength 4}})

(defn attr
  [attr-sym]
  (comp attr-sym :attributes))

((attr :intelligence) character)

(defn coder-comp
  [& fns]
  (fn [& args]
    (let [initial-fn (last fns)
          in-order-fns (reverse (butlast fns))]
      (reduce #(%2 %1) (apply initial-fn args) in-order-fns))))

;; stupid recursive
(defn coder-assoc-in
  [m [k & ks] v]
  (cond
    (empty? ks) (assoc m k v)
    (contains? m k) (assoc m k (coder-assoc-in (k m) ks v))
    :else (assoc m k (coder-assoc-in {} ks v))))

(coder-assoc-in {:bar 2} [:foo] 1)
(coder-assoc-in {:bar 2} [:foo :baz] 1)
(coder-assoc-in {:bar 2 :baz {:buz 1}} [:baz :foo] 3)

(defn coder-update-in
  [m ks f & args]
  (let [value (apply f (cons (get-in m ks) args))]
    (assoc-in m ks value)))

(coder-update-in {} [:foo] #(str %))
(coder-update-in {:foo 1} [:foo] #(str %1 %2) 3)
(coder-update-in {:foo {:bar 1}} [:foo :bar] #(+ 1 %))


(coder-update-in {} [:foo :bar] #(str %))
(coder-update-in {:foo 1} [:foo] #(str %))
(coder-update-in {:foo {:bar 1}} [:foo :bar] #(+ %1 %2) 1)


(update-in {} [:foo] #(str %))
(update-in {} [:foo :bar] #(str %))
(update-in {:foo 1} [:foo] #(str %))
(update-in {:foo {:bar 1}} [:foo :bar] #(+ %1 %2) 1)
