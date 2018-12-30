(ns clj-game-of-life.gol
  (:require [clojure.string :as str]))

(defn- to-vectors [input] (vec (map vec input)))

(defn- is-alive? [field x y]
  (let [result (get (get field y) x)]
    (if (nil? result) 0 result)))

(defn- count-alive-neighbors [field x y]
  ;; cme: using list comprehension like in algebra/haskel (e.g. for all [x,y] where x > 2*y) is done using :when condition
  (apply + (map (fn [[dx dy]] (is-alive? field (+ x dx) (+ y dy)))
                (for [dx (range -1 2) dy (range -1 2) :when (or (not= dx 0) (not= dy 0))] [dx dy]))))

(defn- lives-in-next-generation [field x y]
  ; Here we apply the default GoL rules
  (let [alive-neighbors (count-alive-neighbors field x y)]
    (or (= 3 alive-neighbors)
        (and (= 2 alive-neighbors) (= 1 (is-alive? field x y))))))

(defn- cell-next-generation [field x y]
  (if (lives-in-next-generation field x y) 1 0))

(defn next-gen [field]
  ; Make sure we have a vector, as we need random access to the elements. This is not possible with lists.
  (to-vectors
    ; Iterate the rows
    (map-indexed
      (fn [idr row]
        ; Iterate the columns and calculate the next generation of every cell
        (map-indexed (fn [idc _] (cell-next-generation field idc idr)) row))
      field)))

(defn- line-to-string [line]
  (apply str (map #(if (= 0 %) "." "o") line)))

(defn- board-to-string [lines]
  (str/join "\n" (map line-to-string lines)))

(defn create-board [dx dy]
  (to-vectors
    (partition dx (repeat (* dx dy) 0))))

(defn put-pattern [board x y pattern]
  (to-vectors
    (map-indexed
      (fn [idr row]
        (map-indexed
          (fn [idc it]
            (let [in-pattern (get (get pattern (- idr y)) (- idc x))]
              (if (= 1 in-pattern) in-pattern it)))
          row))
      board)))

(defn put-patterns [board xy-patterns]
  (loop [rest-patterns xy-patterns result board]
    (if (empty? rest-patterns)
      result
      (recur (rest rest-patterns) (apply put-pattern (cons result (first rest-patterns)))))))

(defn run [initial-board rounds sleepMs]
  ; Some debug output ... to see if we are running
  (println "Let's go")
  (let [current (atom initial-board)]
    ; 'doall' forces evaluation of the lazy seq
    (doall (for [x (range rounds)]
             ; Using let to pre-calculate the printable output of the next step (to avoid flickering)
             (let [output (board-to-string (deref current))]
               ; Print 50 empty lines. Is there a better way?
               (println (apply str (repeat 50 "\n")))
               ; Print the # round
               (println (str "Round " x))
               ; Print the board
               (println output)
               ; Calculate the next step and set it on current. How does this work exactly?
               (swap! current next-gen)
               ; Wait a moment
               (Thread/sleep sleepMs))
             )))
  ; Some debug output ... to see if we are done
  (println "Done"))
