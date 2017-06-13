(ns clojure-workbook.chapter-13)

(defmulti full-moon-behaviour (fn [creature] (:type creature)))

(defmethod full-moon-behaviour :wolf
  [creature]
  (str (:name creature) " will howl"))

(defmethod full-moon-behaviour :human
  [creature]
  (str (:name creature) " is chilling"))

(full-moon-behaviour {:type :wolf
                      :name "Carl"})

(full-moon-behaviour {:type :human
                      :name "Frank"})

(defprotocol WereCreature
  (full-moon-behaviour-protocol [x]))

;; Interesting note that redifinint the record does not influence already
;; created records this makes sense from an implementation perspektive but is
;; suprising I wonder how CL behaves in this case using defclass, and defgeneric
(defrecord WereSimmons [name behaviour]
  WereCreature
  (full-moon-behaviour-protocol [x]
    (str name " " behaviour)))

(def simmons (->WereSimmons "Frank" "howl at the moon"))

(full-moon-behaviour-protocol simmons)

(defprotocol Mage
  (spell [x]))

(extend-type WereSimmons
  Mage
  (spell [x]
    "I can't cast"))

(spell simmons)

(defrecord Barberian [name strength health])
(defrecord Witch [name magic-power health])

(def conan (->Barberian "Conan" 10 30))
(def paul (->Barberian "Paul" 1 11))
(def emma (->Witch "Emma" 20 11))

(defprotocol Fighter
  (attack [x victim]))

(extend-protocol Fighter
  Barberian
  (attack [x victim]
    (let [new-health (- (:health victim) (:strength x))]
      (assoc victim :health new-health)))

  Witch
  (attack [x victim]
    (let [new-health (- (:health victim) (:magic-power x))]
      (assoc victim :health new-health))))


(attack conan paul)

(attack emma conan)
