(ns parte-uno.grafo
    (:require
        [parte-uno.vertice :as v]
        [parte-uno.arista :as a]
    )
)

(defn agregar-arista-a-adyacencias [adyacencias arista]
    (
        assoc ...
    )
)

(defprotocol Grafo 
    (agregar [_ aristas])
)
(defrecord rGrafo [adyacencias]
    Grafo
    (agregar [yo aristas]
        (if (= 0 (count aristas))
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