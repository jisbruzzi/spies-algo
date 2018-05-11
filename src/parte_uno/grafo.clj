(ns parte-uno.grafo
    (:require
        [parte-uno.vertice :as v]
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
    (agregar [_ aristas])
)
(defrecord rGrafo [adyacencias]
    Grafo
    (agregar [yo aristas]
        (if (empty? aristas))
            yo
            (let [
                arista (first aristas)
                adyacencias-nuevas (agregar-arista-a-adyacencias adyacencias arista)
                grafo-nuevo (rGrafo. adyacencias-nuevas)

            ]
                (agregar grafo-nuevo (rest aristas))
            )
        )
    
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
