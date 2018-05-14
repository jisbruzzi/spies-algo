(ns parte-uno.dijkstra
    (:require 
        [parte-uno.grafo :as g]
        [parte-uno.problema :as p]
        [parte-uno.estado-dijkstra :as e]
    )
)

(defrecord problema-dijkstra [visitados estado grafo hasta]
    p/Problema
    (terminado? [_]
        (or
            (every? visitados hasta)
            (nil? (e/sin-siguiente-a-visitar estado))
        )
    )
    (conclusion [_]
        (let [
            vertices (g/vertices grafo)
        ]
            (zipmap vertices (map (fn [x] (e/distancia estado x)) vertices))
        )
        
    )
    (alternativa [_]
        (let [
            actual (e/siguiente-a-visitar estado)
            vecinos (filter (complement visitados) (g/vecinos-de grafo actual))

            distancias-actual-vecino (map (fn [x] (g/distancia grafo actual x)) vecinos)
            estado (e/distancias-a-traves-de estado actual vecinos distancias-actual-vecino)
            estado (e/sin-siguiente-a-visitar estado)

            visitados-actualizados (conj visitados actual)
        ]

            (problema-dijkstra. 
                visitados-actualizados
                estado
                grafo
                hasta
            )
        )
    )
    (distancias? [_]
        (e/distancias? estado)
    )
    (grafo? [_]
        grafo
    )
)

(defn problema-con-pesos [grafo aeropuerto espias]
    (problema-dijkstra. 
        #{}
        (e/crear-estado aeropuerto)
        grafo
        espias
    )
)