(ns clojure-workbook.chapter-8)

(defmacro infix
  [[a b c]]
  (list b a c))

(infix (1 + 2))

`(+ 1 ~(inc 2))

(defn critic
  [s c]
  `(println ~s (quote ~c)))

(defmacro code-critic
  [bad good]
  `(do ~@(map #(apply critic %)
              [["Good " good]
               ["Bad " bad]])))

(code-critic (1 + 1) (+ 1 1))

(def order-details
  {:name "Phil"
   :email "phil@example.com"})

(def order-details-validations
  {:name ["name can't be empty" not-empty]
   :email ["email can't be empty" not-empty
           "not a valid email" #(re-seq #"@" %)]})

(defn error-messages-for
  [value field-validations]
  (map first
       (filter #(not ((second %) value)) (partition 2 field-validations))))

(error-messages-for "phil" ["error" not-empty])
(error-messages-for "" ["error" not-empty])

(defn validate
  [record validations]
  (reduce (fn [errors [fieldname field-validation]]
            (let [error-messages (error-messages-for (get record fieldname) field-validation)]
              (if (empty? error-messages)
                errors
                (assoc errors fieldname error-messages))))
          {}
          validations))

(validate order-details order-details-validations)
(validate {:name "" :email "foobar"} order-details-validations)

(defmacro if-valid
  [record validations errors-var & then-else]
  `(let [~errors-var (validate ~record ~validations)]
     (if (empty? ~errors-var)
       ~@then-else)))

(if-valid order-details order-details-validations errors
          (println :succes)
          (println :error errors))

(if-valid {:name "" :email "foobar"} order-details-validations errors
          (println :succes)
          (println :error errors))

(defmacro when-valid
  [record validations & then]
  `(if-valid ~record ~validations ~(gensym)
             (do ~@then)))

(when-valid order-details order-details-validations
            (println :succes)
            1)

(when-valid {:name "" :email "foobar"} order-details-validations
            (println :succes)
            1)


(defmacro my-or
  ([] nil)
  ([x] x)
  ([x & xs]
   `(let [or# ~x]
      (if or# or# (or ~@xs)))))


(my-or)
(my-or false)
(my-or true true)
(my-or true false)
(my-or true false false)
(my-or false false true)
(my-or (= 1 2) (= 2 1) (= 1 1))
(my-or (println 1) true (println 2))

(defmacro defattrs
  [& attrs]
  (map (fn [[attr-reader attr]] `(defn ~attr-reader [record#] (~attr record#)))
       (partition 2 attrs)))


(defattrs
  c-int :intelligence
  c-str :strength)


(c-int {:intelligence 1})
(c-str {:strength 1})
