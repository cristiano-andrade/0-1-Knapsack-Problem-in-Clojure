(ns knapsack.core
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as str])
  (:require [clojure.pprint])
  (:gen-class))

(defn calculate-crosstab
  [dolls max-weight]

  (def crosstab [])

  ; for each doll ...
  (dotimes [a (count dolls)]
    (def row [])

    ; for each weight from 0 to max-weight ...
    (dotimes [b (+ max-weight 1)]
      (if (= b 0)
        (def row (conj row 0))

        ; if this is the first item ...
        (if (= a 0)

          ; compare item weight to weight ...
          (if (<= (get (nth dolls a) :weight) b)
            (def row (conj row (get (nth dolls a) :value)))
            (def row (conj row 0))
          )

          ; if previous row index - item weight is not inbounds ...
          (if (< (- b (get (nth dolls a) :weight)) 0)
            (def row (conj row (nth (last crosstab) b)))

            ; if it is inbounds ...
            (if
              ; if the item doesn't add value ...
              (<=
                (+
                  (get (nth dolls a) :value)
                  (nth (last crosstab) (- b (get (nth dolls a) :weight)))
                )
                (nth (last crosstab) b)
              )

              (def row (conj row (nth (last crosstab) b)))
              (def row (conj row (+ (get (nth dolls a) :value) (nth (last crosstab) (- b (get (nth dolls a) :weight))))))
            )
          )
        )
      )
    )

    (def crosstab (conj crosstab row))
  )

  crosstab
)

(defn create-crosstab
  [dolls max-weight]

  (def max-doll-index (dec (count dolls)))

  (if (or (< max-doll-index 0) (= max-weight 0))
    []

    (calculate-crosstab dolls max-weight)
  )
)

(defn get-lines
  "Converts a file to a vector with each line as an element"
  [filename]

  (def lines [])
  (with-open [rdr (io/reader filename)]
    (doseq [line (line-seq rdr)]
      (def lines (conj lines line))))
  lines)

(defn find-max-weight
  "Given a string with digits, returns the digits as an integer"
  [string]

  (def digits (re-find #"\d+" string))
  (def digits (Integer. digits))
  digits)

(defn find-dolls
  "Returns a vector of dolls"
  [lines]

  (def dolls (filter #(re-find #"\w+\s+\d+\s+\d+" %) lines))
  (def dolls (map (fn [string] (str/split string #"\s+")) dolls))
  (def dolls (vec (map (fn [doll-vector] (zipmap [:name :weight :value] doll-vector)) dolls)))
  (def dolls (map (fn [doll] {:name (get doll :name) :weight (Integer. (get doll :weight)) :value (Integer. (get doll :value))}) dolls))
  dolls
)

(defn select-dolls
  "Selects optimal dolls to put in the bag"
  [dolls max-weight]


)



(defn -main
  "Given a properly formatted input file, selects dolls"
  [filename]

  (def lines (get-lines filename))
  (def max-weight (find-max-weight (nth lines 0)))
  (def dolls (find-dolls lines))

  (def crosstab (create-crosstab dolls max-weight))

  (clojure.pprint/pprint crosstab)
)
