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
            vecinos-no-visitados (filter (complement visitados) (g/vecinos-de grafo actual))

            distancias-actual-vecino (map (fn [x] (g/distancia grafo actual x)) vecinos-no-visitados)
            estado (e/distancias-a-traves-de estado actual vecinos-no-visitados distancias-actual-vecino)
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
)

(defn espias-mas-cerca [grafo aeropuerto espias]
    (p/solucion-greedy (problema-dijkstra. 
        #{}
        (e/crear-estado aeropuerto)
        grafo
        espias
    ))
)