(ns parte-uno.arista
    (:require 
        [parte-uno.vertice :as v]
        [clojure.string :as str]
    )    
)

(defprotocol Arista (vertices [_]))

(defrecord rArista [v1 v2]
    Arista
    (vertices [_] (list v1 v2))
)

(defn crear [string] 
  (let [
    separado (str/split string #" ")
    filtrado (filter (fn [s] (re-matches #"[0-9]+" s)) separado)
    nros (map (fn [s] (Integer. s)) filtrado)
  ]
    (if (= 4 (count nros))
        (rArista. 
            (v/crear (nth nros 0) (nth nros 1))
            (v/crear (nth nros 2) (nth nros 3))
        )
        nil
    )
  )
)