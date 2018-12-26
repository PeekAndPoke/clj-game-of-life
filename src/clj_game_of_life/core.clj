(ns clj-game-of-life.core
  (:require [clj-game-of-life.gol :as gol]))

;; Why do we have the minus here: "-main" ... saw it somewhere
(defn -main []
  (println "Hello Game Of Life!")
  ;; Run the GoL for 500 generations with 25 ms of sleep time in between
  (gol/run 500 25)
  (println "ByeBye Game Of Life!"))
