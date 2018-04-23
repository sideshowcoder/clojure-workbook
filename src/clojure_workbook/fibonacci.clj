(ns clojure-workbook.fibonacci)


(defn fib
  [n]
  (condp = n
    0 0
    1 1
    (+ (fib (- n 1)) (fib (- n 2)))))

(time (fib 33))


(defn fib-loop
  [n]
  (loop [[prevprev prev] [(bigint 0) (bigint 1)]
         curr (- n 1)]
    (if (zero? curr) prev
        (recur [prev (+ prevprev prev)] (dec curr)))))

(time (fib-loop 100))


(defn fib-reduce
  [n]
  (reduce (fn [[a b] curr]
            (if (zero? curr) b
                [b (+' a b)]))
          [0 1]
          (range (- n 1) -1 -1)))

(time (fib-reduce 100))
