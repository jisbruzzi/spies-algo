(ns parte-uno.bfs
    (:require 
        [parte-uno.grafo :as g]
        [parte-uno.problema :as p]
    )
)


(defrecord problema-bfs [cola visitados distancias mayor-distancia grafo hasta]
    p/Problema
    (terminado? [_]
        (or 
            (every? (fn [h]
                (and 
                    (visitados h)
                    (< (distancias h) mayor-distancia)
                )
            ) hasta) 
            (empty? cola)
        )
        
    )
    (conclusion [_]
        distancias
    )
    (alternativa [_]
        (let [
            actual (first cola)
            desacolada (rest cola)
            vecinos (g/vecinos-de grafo actual)
            vecinos (filter (complement visitados) vecinos)
            vecinos (filter (complement (set cola)) vecinos)

            sig-cola (concat desacolada vecinos)
            sig-visitados (conj visitados actual)

            distancia-vecinos  (+ (get distancias actual) 1)
            distancias-vecinos (map (fn [x] distancia-vecinos) vecinos)
            distancias (into distancias (zipmap vecinos distancias-vecinos))
        ]
            (problema-bfs. sig-cola sig-visitados distancias distancia-vecinos grafo hasta)
        )
    )
    (distancias? [_]
        distancias
    )
    (grafo? [_]
        grafo
    )
)

(defn problema-sin-pesos [grafo aeropuerto espias]
    (problema-bfs. (list aeropuerto) #{} {aeropuerto 0} 0 grafo espias)
)