(ns clj-game-of-life.core
  (:require [clj-game-of-life.gol :as gol]
            [clj-game-of-life.patterns :as patterns]))

;; Why do we have the minus here: "-main" ... saw it somewhere
(defn -main []
  (println "Hello Game Of Life!")
  ;; Run the GoL for 500 generations with 25 ms of sleep time in between
  (gol/run
    (gol/put-patterns (gol/create-board 80 50) [[0 0 patterns/cosper-glider-gun]
                                                [50 40 patterns/still-block]
                                                [55 36 patterns/still-block]])
    1500
    15)
  (println "ByeBye Game Of Life!"))
