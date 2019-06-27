(ns clojure-workbook.workbook)

(defn fizzbuzz
  "Classic FizzBuzz:

  Write a program that prints the numbers from 1 to 100. But for multiples of
  three print “Fizz” instead of the number and for the multiples of five print
  “Buzz”. For numbers which are multiples of both three and five print
  “FizzBuzz”."
  [n]
  (letfn [(buzz? [x] (and (number? x) (zero? (mod x 5))))
          (fizz? [x] (and (number? x) (zero? (mod x 3))))
          (fizzbuzz? [x] (and (fizz? x) (buzz? x)))]
    (->> (range 1 (+ n 1))
         (map #(if (fizzbuzz? %) "FizzBuzz" %))
         (map #(if (buzz? %) "Buzz" %))
         (map #(if (fizz? %) "Fizz" %)))))

(fizzbuzz 100)
