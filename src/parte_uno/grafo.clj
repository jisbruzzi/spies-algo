(ns parte-uno.grafo
    (:require
        [parte-uno.vertice-ady :as v]
        [parte-uno.arista :as a]
    )
)

(defn agregar-arista-a-adyacencias [ady arista]
    (let [
        v (vertices? arista)
        v1 (nth v 1)
        a1 (get adyacencias v1)
        v2 (nth v 2)
        a2 (get adyacencias v2)

        ady1 (assoc ady v1 (conj a1 v1))
        ady2 (assoc ady1 v2 (conj a2 v2))
      ]
      ady2
    )
)

(defprotocol Grafo 
    (con-aristas [_ aristas])
)
(defrecord rGrafo [vertices aristas]
    Grafo
    (con-aristas [yo aristas-n]
        (if (empty? aristas-n)
            yo
            (let [
                arista (first aristas-n)
                grafo-nuevo (con-arista arista)

            ]
                (agregar grafo-nuevo (rest aristas))
            )
        )
    )
    (con-arista [yo arista]
      (let [
          vs (a/vertices-adyacentes? arista)
          gv1 (con-vertice yo  (nth vs 0))
          gv2 (con-vertice gv1 (nth vs 1))
        ]
        gv2
      )
    )
    (con-vertice [yo vertice]

    )
)

(defn crear [str]
    (let [
        aristas (map a/crear (str/split-lines str) )
        grafo-vacio (rGrafo. {})
        grafo-definitivo (agregar grafo-vacio aristas)
    ])
    
    grafo-definitivo
)
