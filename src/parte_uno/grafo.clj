(ns parte-uno.grafo
    (:require
        [parte-uno.vertice :as v]
        [parte-uno.arista :as a]
        [clojure.string :as str]
        [clojure.math.numeric-tower :as math]
    )
)


(defprotocol Grafo
    (vecinos-de [_ v])
    (distancia [_ u v])
    (vertices [_])
)
(defprotocol GrafoCreable
    (adyacencias? [_])
)
(defrecord rGrafo [adyacencias]
    GrafoCreable
    (adyacencias? [_] adyacencias)

    Grafo
    (vecinos-de [_ v]
        (get adyacencias v)
    )
    (distancia [_ u v]
        (let [
            dx (- (:x u) (:x v))
            dy (- (:y u) (:y v))
        ]
            (math/sqrt (+ (* dx dx) (* dy dy) ) )
        )
    )
    (vertices [yo]
        (keys adyacencias)
    )
)



(defn crear-grafo-desde-arista [a]
    (let [
        u (nth a 0)
        v (nth a 1)
    ]
        (rGrafo. {v (list u), u (list v)})
    )
    
)

(defn combinar-grafos [g h]
    (let [
        adyg (adyacencias? g)
        adyh (adyacencias? h)
        todos (merge-with concat adyg adyh)
        ady (rGrafo. todos)
    ]
        ady
    )
)


(defn crear [str]
    (let [
        aristas (map a/crear (str/split-lines str) )
        grafitos (map crear-grafo-desde-arista aristas)
        grafo-definitivo (reduce combinar-grafos grafitos)
    ]
        grafo-definitivo
    )
)