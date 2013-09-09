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

(deftest test-crosstab
  (testing "knapsack.core/create-crosstab returns a 'table' of max values at given weight-item"
    ;returns empty vector if no dolls
    (is (= (knapsack.core/create-crosstab [] 5 ) []))
    ;returns empty vector if max-weight 0
    (is (= (knapsack.core/create-crosstab [1 2 3] 0 ) []))

    (def test-dolls [{:name "one" :weight 2 :value 3}])
    (is (= (knapsack.core/create-crosstab test-dolls 5) [[0 0 3 3 3 3]]))

    (def test-dolls (conj test-dolls {:name "two" :weight 3 :value 4}))
    (is (= (knapsack.core/create-crosstab test-dolls 5) [[0 0 3 3 3 3][0 0 3 4 4 7]]))
  ))
