(ns clj-game-of-life.core
  (:require [clj-game-of-life.gol :as gol]))

(defn -main []
  (println "Hello Game Of Life!")
  (gol/run 500 25)
  (println "ByeBye Game Of Life!"))
