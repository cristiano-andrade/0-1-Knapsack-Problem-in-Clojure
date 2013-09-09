(ns knapsack.core
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as str])
  (:require [clojure.pprint])
  (:gen-class))

(defn find-indexes
  [max-weight max-total-value crosstab dolls indexes]

  ; return indexes if max-weight 0 or max-total-value 0
  (if (or (= max-total-value 0)(= max-weight 0))
    indexes

    ; else ...
    (do
      (def index-of-doll(.indexOf (map (fn [x] (nth x max-weight)) crosstab) max-total-value))

      ; return indexes if max-total-value not found
      (if (= -1 index-of-doll)
        indexes

        (do
          (def new-indexes (into [index-of-doll] indexes))
          (def new-max-weight (- max-weight (get (nth dolls index-of-doll) :weight)))
          (def new-max-total-value (- max-total-value (get (nth dolls index-of-doll) :value)))
          (find-indexes new-max-weight new-max-total-value crosstab dolls new-indexes)
        )))))


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

(defn format-dolls
  [dolls]

  (def output (map (fn[x] [(get x :name) (get x :weight) (get x :value)]) (reverse dolls)))
  (def output (map (fn[x] (str/join "\t\t" x)) output))
  (def output (into ["name\t\tweight\t\tvalue"] output))
  (def output (into [""] output))
  (def output (into ["packed dolls:"] output))
  output
)

(defn display-to-screen
  [lines]
  (dotimes [x (count lines)]
    (println (nth lines x))
  )
)




(defn -main
  "Given a properly formatted input file, selects dolls"
  [filename]

  (def lines (get-lines filename))
  (def max-weight (find-max-weight (nth lines 0)))
  (def dolls (find-dolls lines))
  (def crosstab (create-crosstab dolls max-weight))
  (def max-total-value (last (last crosstab)))
  (def dolls-to-select-indexes (find-indexes max-weight max-total-value crosstab dolls []))
  (def selected-dolls (map (fn[x] (nth dolls x)) dolls-to-select-indexes))
  (def to-print (format-dolls selected-dolls))
  (display-to-screen to-print)
  ; to-print
)
