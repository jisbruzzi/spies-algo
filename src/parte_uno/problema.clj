(ns parte-uno.problema
    (:require 
    [parte-uno.grafo :as g]
  )
)


(defprotocol Problema 
    (terminado? [_])
    (conclusion [_])
    (alternativa [_])
    (distancias? [_])
    (grafo? [_])
)

(defn analisis-greedy [problema]
    (if (terminado? problema)
        (conclusion problema) ;devuelve nil si no hay solucion
        (analisis-greedy (alternativa problema))
    )
)

(defn conserva-documentos [distancias espia-con-documentos espia-sin-documentos]
    (let [
        distancia-espia-con-documentos (or (distancias espia-con-documentos) (/ 1.0 0.0))
        distancia-espia-sin-documentos (or (distancias espia-sin-documentos) (/ 1.0 0.0))
    ]
        (< distancia-espia-sin-documentos distancia-espia-con-documentos)
    )
)

(defn prrr [x]
    (let [
        y (println x)
    ]
        x
    )
)

(defn camino-desde [distancias grafo desde]
    
        (if (= 0 (distancias desde))
            (list desde)
            (concat (list desde) (camino-desde 
                distancias
                grafo 
                (:v (reduce 
                    (fn mas-cercano 
                        [v1 v2]
                        
                        (if (<  (:d  v1)  (:d  v2))
                            v1
                            v2
                        )
                        
                    )
                    (map 
                        (fn d-v [v]
                            {:d (or (distancias v) (/ 1.0 0.0)), :v v}
                        )
                        (prrr (g/vecinos-de grafo desde))
                    )
                ))
            ))
        )
    
    
)