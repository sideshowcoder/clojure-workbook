(ns clojure-workbook.chapter-7
  (:gen-class))

(eval
 (let [name-and-movie (read-string "(phil alien)")]
   (list 'println (str "my name is " (first name-and-movie) " movie is " (second name-and-movie)))))

(def operators '(+ - * /))

(def infix-operator-transforms
  (apply comp (map #(partial transform-operator %) operators)))

(defn infix-transform
  [infix-ops]
  (first (infix-operator-transforms infix-ops)))

(defn transform-operator
  [operator operations]
  (loop [[op1 op2 & ops] operations
         res (list)]
    (cond
      (nil? op1) (reverse res)
      (= op1 operator) (let [[hd & tl] res] (recur ops (cons (list operator hd op2) tl)))
      :else (recur (cons op2 ops) (cons op1 res)))))

(defn infix-eval
  [infix-ops]
  (eval (infix-transform infix-ops)))

(infix-transform '())
;; (1)
(infix-transform '(1))
;; (+ 1 2)
(infix-transform '(1 + 2))
;; (+ (* 2 3 ) 4)
(infix-transform '(2 * 3 + 4))
;; (+ 2 (* 3 4))
(infix-transform '(2 + 3 * 4))

(infix-eval '(2 + 3 * 4))
(infix-eval '(2 * 3 + 4))
