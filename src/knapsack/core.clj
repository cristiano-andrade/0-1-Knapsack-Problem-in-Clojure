(ns knapsack.core
  (:require [clojure.java.io :as io])
  (:gen-class))

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



(defn -main
  "Given a properly formatted input file, selects dolls"
  [filename]

  (def lines (get-lines filename))
  (def max-weight (find-max-weight (nth lines 0)))

  (println max-weight)
)
