(ns clojure-workbook.chapter-11
  (:require [clojure.core.async
             :as async
             :refer [>! <! >!! <!! go chan buffer close! thread alts! alts!! timeout]]
            [clojure.string
              :as s]))


(def echo-chan (chan))

(go (loop []
      (let [msg (<! echo-chan)]
        (cond
          (= msg :exit) (println "Goodbye")
          :else (do
                  (println msg)
                  (recur))))))

(doseq [n (range 10)]
  (go (>! echo-chan (str "hello " n))))

(>!! echo-chan :exit)


(defn hot-dog-machine
  [count]
  (let [in (chan)
        out (chan)]
    (go (loop [c count]
          (println c)
          (if (> c 0)
            (let [value (<! in)]
              (if (= value 3)
                (do (>! out "thing")
                    (recur (dec c)))
                (do (>! out "no thing for you!")
                    (recur c))))
            (do
              (close! in)
              (close! out)))))
    [in out]))

(let [[in out] (hot-dog-machine 2)]
  (>!! in "Nothing!")
  (println (<!! out))

  (>!! in 3)
  (println (<!! out))

  (>!! in 3)
  (println (<!! out))

  (>!! in 3)
  (println (<!! out)))

(let [c1 (chan)
      c2 (chan)
      c3 (chan)]
  (go (>! c2 (s/upper-case (<! c1))))
  (go (>! c3 (s/reverse (<! c2))))
  (go (println (<! c3)))
  (>!! c1 "redrum"))


(defn long-echo
  [i c]
  (go (Thread/sleep (rand 100))
      (>! c i)))

(let [c1 (chan)
      c2 (chan)]
  (long-echo "foo" c1)
  (long-echo "bar" c2)
  (let [[winner _] (alts!! [c1 c2])]
    (println winner)))
