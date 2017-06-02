(ns clojure-workbook.chapter-10)

(let [a (atom 0)]
  (dotimes [_ 10]
    (swap! a inc))
  @a)
