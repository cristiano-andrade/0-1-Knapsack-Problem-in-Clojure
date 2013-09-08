(ns knapsack.core-test
  (:require [clojure.test :refer :all]
            [knapsack.core :refer :all]))

(deftest read-in-file
  (testing "Reads lines from file into a vector"
    (is (= (count (knapsack.core/get-lines "test.txt")) 9))
    (is (= (type (knapsack.core/get-lines "test.txt")) clojure.lang.PersistentVector))
  ))

(deftest test-max-weight
  (testing "knapsack.core/find-max-wight returns the maximum weight as an integer"
    (is (= (knapsack.core/find-max-weight "max weight: 1") 1))
    (is (= (type (knapsack.core/find-max-weight "max weight: 1")) Integer))
  ))

(deftest test-find-dolls
  (testing "knapsack.core/find-dolls returns the dolls"
    (is (= (knapsack.core/find-dolls ["doll 1 1"]) [{:name "doll" :weight 1 :value 1}]))
    (is (= (knapsack.core/find-dolls ["abc abc abc" "doll 1 1"]) [{:name "doll" :weight 1 :value 1}]))
    (is (= (knapsack.core/find-dolls ["doll 2 2" "doll 1 1"]) [{:name "doll" :weight 2 :value 2} {:name "doll" :weight 1 :value 1}]))
  ))
