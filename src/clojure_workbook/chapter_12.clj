(ns clojure-workbook.chapter-12
  (:import [java.util Stack]))

(.toUpperCase "foo bar baz")
(.indexOf "foo bar baz" "b")

java.lang.Math/PI

(new String)

(String.)

(String. "foo bar baz")



(doto (Stack.)
  (.push "foo")
  (.push "bar")
  (.push "baz"))


(System/getProperty "user.dir")

(with-open [csv (clojure.java.io/reader "suspects.csv")]
  (last (line-seq csv)))
