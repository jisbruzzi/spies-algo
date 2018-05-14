(ns parte-uno.arista
    (:require 
        [parte-uno.vertice :as v]
        [clojure.string :as str]
    )    
)

(defn crear [string] 
  (let [
    separado (str/split string #" ")
    filtrado (filter (fn [s] (re-matches #"[0-9]+" s)) separado)
    nros (map (fn [s] (Integer. s)) filtrado)
  ]
    (if (= 4 (count nros))
        (list
            (v/crear (nth nros 0) (nth nros 1))
            (v/crear (nth nros 2) (nth nros 3))
        )
        nil
    )
  )
)