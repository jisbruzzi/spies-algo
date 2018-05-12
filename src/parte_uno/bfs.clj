(ns parte-uno.bfs
    (:require 
        [parte-uno.grafo :as g]
        [parte-uno.problema :as p]
    )
)


(defrecord problema-bfs [cola visitados grafo hasta]
    p/Problema
    (terminado? [_]
        (some visitados hasta)
    )
    (conclusion [_]
        (filter visitados hasta)
    )
    (alternativa [_]
        (let [
            actual (first cola)
            desacolada (rest cola)
            vecinos (g/vecinos-de grafo actual)
            vecinos-no-visitados (filter (complement visitados) vecinos)
            vecinos-no-acolados (filter (complement (set cola)) vecinos-no-visitados)
            sig-cola (concat desacolada vecinos-no-acolados)
            sig-visitados (conj visitados actual)
        ]
            (problema-bfs. sig-cola sig-visitados grafo hasta)
        )
    )
)

(defn espias-mas-cerca [grafo aeropuerto espias]
    (p/solucion-greedy (problema-bfs. (list aeropuerto) #{} grafo espias))
)