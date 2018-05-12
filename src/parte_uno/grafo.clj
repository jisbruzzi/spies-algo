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
)
(defprotocol GrafoCreable
    (con-aristas [_ aristas])
    (con-arista [_ arista])
)
(defrecord rGrafo [adyacencias]
    GrafoCreable
    (con-aristas [yo aristas]
        (if (empty? aristas)
            yo
            (let [
                arista (first aristas)
                grafo-nuevo (con-arista yo arista)

            ]
                (con-aristas grafo-nuevo (rest aristas))
            )
        )
    )

    (con-arista [yo arista]
      (let [
          con-arista-v (fn [u v ady]
            (let [
                vecinos-u (get adyacencias u)
                conj-seguro (fnil conj (list))
                nuevos-vecinos-u (conj-seguro  vecinos-u v)
                nueva-ady (assoc ady u nuevos-vecinos-u)
            ]
                nueva-ady
            )
          )
          vs (a/vertices arista)
          v0 (nth vs 0)
          v1 (nth vs 1)
          ady0 (con-arista-v v0 v1 adyacencias)
          ady1 (con-arista-v v1 v0 ady0)
        ]
        (rGrafo. ady1)
      )
    )

    Grafo
    (vecinos-de [_ v]
        (get adyacencias v)
    )
    (distancia [_ u v]
        (let [
            dx (- (v/x? u) (v/x? v))
            dy (- (v/y? u) (v/y? v))
        ]
            (math/sqrt (+ (* dx dx) (* dy dy) ) )
        )
    )
)

(defn crear [str]
    (let [
        aristas (map a/crear (str/split-lines str) )
        grafo-vacio (rGrafo. {})
        grafo-definitivo (con-aristas grafo-vacio aristas)
    ]
        grafo-definitivo
    )
)
