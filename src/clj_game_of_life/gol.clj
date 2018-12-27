(ns clj-game-of-life.gol
  (:require [clojure.string :as str]))

;; lets have the board in custom dim
(def dx 50)
(def dy 50)

;; lets use a compact generator function (fn [i]) that maps on a index seq [0 .. (dx * dy)]
(def ^:private initial (into [] (map vec (partition dx 
                                                    (into [] (let [ ;; approx the matrix center
                                                                   dx' (quot dx 2)
                                                                   dy' (quot dy 2)
                                                                   ;; 
                                                                   unos #{[(dec dy') dx']
                                                                          [(dec dy') (inc dx')]
                                                                          [dy' (dec dx')]
                                                                          [dy' dx']
                                                                          [(inc dy') dx']}] 
                                                               (doall (map 
                                                                       (fn [i]
                                                                         (let [x (mod i dx)
                                                                               y (quot i dy)]                                                
                                                                           (if (contains? unos [y x]) 1 0)))
                                                                       (range (* dx dy))))))))))

(defn- is-alive? [field x y]
  (let [result (get (get field y) x)]
    (if (nil? result) 0 result)))

(defn- count-alive-neighbors [field x y]
  ;; cme: using list comprehension like in algebra/haskel (e.g. for all [x,y] where x > 2*y) is done using :when condition
  (apply + (map (fn [[dx dy]] (is-alive? field (+ x dx) (+ y dy))) (for [dx (range -1 2) dy (range -1 2) :when (or (not= dx 0) (not= dy 0))]
    [dx dy]))
    ))

(defn- lives-in-next-generation [field x y]
  ; Here we apply the default GoL rules
  ;; using let we can memoize results for avoiding recalc
  ;; (un)fortunatelly clojure is not a pure language like haskell so idempotenz does not work, so 
  ;; recuring calculations are not cached by default
  (let [alive-neighbors (count-alive-neighbors field x y)]
    (or (= 3 alive-neighbors)
        (and (= 2 alive-neighbors) (= 1 (is-alive? field x y))))))

(defn- next-generation [field x y]
  (if (lives-in-next-generation field x y) 1 0))

(defn- next-step [field]
  ; Make sure we have a vector, as we need random access to the elements. This is not possible with lists.
  (vec
    (map-indexed
      ; Iterate the rows
      (fn [idr row]
        ; Same deal. We need vectors for random access.
        (vec
          ; Iterate the columns and calculate the next generation of every cell
          (map-indexed (fn [idc _] (next-generation field idc idr)) row))
        )
      field
      )))

(defn- line-to-string [line]
  (apply str (map #(if (= 0 %) "." "o") line)))

(defn- board-to-string [lines]
  (str/join "\n" (map line-to-string lines)))

(def ^:private current (atom initial))

(defn run [rounds sleepMs]
  ; Some debug output ... to see if we are running
  (println "Let's go")
  ; 'doall' forces evaluation of the lazy seq
  (doall (for [x (range rounds)]
           ; Using let to pre-calculate the printable output of the next step (to avoid flickering)
           (let [output (board-to-string (deref current))]
             ; Print 50 empty lines. Is there a better way?
             (repeatedly 50 (println ""))
             ; Print the # round
             (println (str "Round " x))
             ; Print the board
             (println output)
             ; Calculate the next step and set it on current. How does this work exactly?
             (swap! current next-step)
             ; Wait a moment
             (Thread/sleep sleepMs))
           ))
  ; Some debug output ... to see if we are done
  (println "Done"))
