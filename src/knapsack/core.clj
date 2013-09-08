(ns knapsack.core
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as str])
  (:require [clojure.pprint])
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

(defn find-dolls
  "Returns a vector of dolls"
  [lines]

  (def dolls (filter #(re-find #"\w+\s+\d+\s+\d+" %) lines))
  (def dolls (map (fn [string] (str/split string #"\s+")) dolls))
  (def dolls (vec (map (fn [doll-vector] (zipmap [:name :weight :value] doll-vector)) dolls)))
  (def dolls (map (fn [doll] {:name (get doll :name) :weight (Integer. (get doll :weight)) :value (Integer. (get doll :value))}) dolls))
  dolls
)


(defn -main
  "Given a properly formatted input file, selects dolls"
  [filename]

  (def lines (get-lines filename))
  (def max-weight (find-max-weight (nth lines 0)))
  (def dolls (find-dolls lines))

  (clojure.pprint/pprint (type (get (nth dolls 0) :weight)))
)
