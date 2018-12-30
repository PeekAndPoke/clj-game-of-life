(ns clj-game-of-life.gol-test
  (:require [clojure.test :refer :all]
            [clj-game-of-life.gol :refer :all]
            [clj-game-of-life.patterns :as patterns]))

(deftest create-board-test
  (is (= (create-board 0 0) []))
  (is (= (create-board 1 1) [[0]]))
  (is (= (create-board 2 2) [[0 0] [0 0]]))
  )

(deftest put-pattern-test

  (is (= (put-pattern (create-board 5 5) 1 1 patterns/still-block)
         [[0 0 0 0 0]
          [0 1 1 0 0]
          [0 1 1 0 0]
          [0 0 0 0 0]
          [0 0 0 0 0]]))

  (is (= (put-pattern (create-board 5 5) 1 1 patterns/still-loaf)
         [[0 0 0 0 0]
          [0 0 1 1 0]
          [0 1 0 0 1]
          [0 0 1 0 1]
          [0 0 0 1 0]]))
  )

(deftest next-gen-test

  (is (= (next-gen [[0 0 0 0 0]
                    [0 1 1 1 0]
                    [0 0 0 0 0]])
         [[0 0 1 0 0]
          [0 0 1 0 0]
          [0 0 1 0 0]]))

  (is (= (next-gen [[0 0 0 0 0 0]
                    [0 0 0 0 0 0]
                    [0 0 1 1 1 0]
                    [0 1 1 1 0 0]
                    [0 0 0 0 0 0]
                    [0 0 0 0 0 0]])
         [[0 0 0 0 0 0]
          [0 0 0 1 0 0]
          [0 1 0 0 1 0]
          [0 1 0 0 1 0]
          [0 0 1 0 0 0]
          [0 0 0 0 0 0]]))
  )
