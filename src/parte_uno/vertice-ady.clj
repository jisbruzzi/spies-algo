(ns parte-uno.vertice-ady
  (:require [vertice :as v]
            [arista :as a]
  )
)

(defprotocol VerticeAdy 
  (con-arista [_ a])
  (vecinos [_])
)

(defrecord rVerticeAdy [vertice aristas]
  VerticeAdy
  (con-arista [yo a]
    (if (a/tiene? yo)
      (rVerticeAdy. vertice (conj aristas a))
      yo
    )
  )
  (vecinos [yo]
    (map (fn [a] (a/otro a yo) aristas))
  )
  v/Vertice
  (x? [_] (x? vertice))
  (y? [_] (y? vertice))
)

