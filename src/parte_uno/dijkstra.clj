(ns parte-uno.dijkstra
    (:require 
        [parte-uno.grafo :as g]
        [parte-uno.problema :as p]
        [parte-uno.distancias :as d]
    )
)

(defrecord problema-dijkstra [visitables visitados distancias grafo hasta]
    p/Problema
    (terminado? [_]
        (every? visitados hasta)
    )
    (conclusion [_]
        distancias;(zipmap hasta (map distancias hasta))
    )
    (alternativa [_]
        (let [
            actual (nth (first visitables) 1)
            vecinos-no-visitados (filter (complement visitados) (g/vecinos-de grafo actual))

            distancia-actual (d/distancia distancias actual)
            distancias-actual-vecino (map (fn [x] (g/distancia grafo actual x)) vecinos-no-visitados)
            distancias-actualizadas (d/distancias-a-traves-de distancias actual vecinos-no-visitados distancias-actual-vecino)

            visitables-sin-actual (remove #{actual} visitables)
            visitados-actualizados (conj visitados actual)

            distancias-nuevas (map (fn [x] (d/distancia distancias x)) vecinos-no-visitados)
            visitables-actualizados (into visitables-sin-actual (zipmap distancias-nuevas vecinos-no-visitados))

            a (println "-------------------")
            x (println visitados)
            z (println actual)
            y (println visitables-actualizados)
        ]

            (problema-dijkstra. 
                visitables-actualizados
                visitados-actualizados
                distancias-actualizadas
                grafo
                hasta
            )
        )
    )
)

(defn espias-mas-cerca [grafo aeropuerto espias]
    (p/solucion-greedy (problema-dijkstra. 
        (sorted-map 0 aeropuerto)
        #{}
        (d/crear-distancias {aeropuerto 0})
        grafo
        espias
    ))
)