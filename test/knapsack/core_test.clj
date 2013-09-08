(ns knapsack.core-test
  (:require [clojure.test :refer :all]
            [knapsack.core :refer :all]))

(deftest read-in-file
  (testing "Reads lines from file into a vector"
    (is (= (count (knapsack.core/get-lines "test.txt")) 9))
    (is (= (type (knapsack.core/get-lines "test.txt")) clojure.lang.PersistentVector))
  ))
