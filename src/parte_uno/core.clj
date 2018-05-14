(ns parte-uno.core
  (:gen-class)
  (:require 
    [clojure.string :as str]
    [parte-uno.grafo :as g]
    [parte-uno.bfs :as bfs]
    [parte-uno.dijkstra :as djk]
    [parte-uno.vertice :as v]
    [parte-uno.problema :as p]
  ))



(defn -main
  "parte 1 del TP de TDA"
  [& args]
  (println 
    (p/camino-desde
      (p/analisis-greedy (djk/problema-con-pesos ;bfs/problema-sin-pesos
        (g/crear (slurp "mapa.coords")) 
        (v/crear 398 122) 
        [(v/crear 115 273) (v/crear 296 278) (v/crear 227 180)]
      ))
      (g/crear (slurp "mapa.coords")) 
      (v/crear 296 278)
    )
    

  )
)