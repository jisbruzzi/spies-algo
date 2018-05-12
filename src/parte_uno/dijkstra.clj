(ns parte-uno.dijkstra
    (:require 
        [parte-uno.grafo :as g]
        [parte-uno.problema :as p]
        [parte-uno.estado-dijkstra :as d]
    )
)

(defrecord problema-dijkstra [visitados distancias grafo hasta]
    p/Problema
    (terminado? [_]
        (every? visitados hasta)
    )
    (conclusion [_]
        distancias;(zipmap hasta (map distancias hasta))
    )
    (alternativa [_]
        (let [
            actual (d/siguiente-a-visitar distancias)
            vecinos-no-visitados (filter (complement visitados) (g/vecinos-de grafo actual))

            distancias-actual-vecino (map (fn [x] (g/distancia grafo actual x)) vecinos-no-visitados)
            distancias (d/distancias-a-traves-de distancias actual vecinos-no-visitados distancias-actual-vecino)
            distancias (d/sin-siguiente-a-visitar distancias)

            visitados-actualizados (conj visitados actual)

            a (println "-------------------")
            x (println visitados)
            x (println distancias)
            z (println actual)
        ]

            (problema-dijkstra. 
                visitados-actualizados
                distancias
                grafo
                hasta
            )
        )
    )
)

(defn espias-mas-cerca [grafo aeropuerto espias]
    (p/solucion-greedy (problema-dijkstra. 
        #{}
        (d/crear-distancias aeropuerto)
        grafo
        espias
    ))
)