(ns parte-uno.core
  (:gen-class)
  (:require 
    [clojure.string :as str]
    [parte-uno.grafo :as g]
    [parte-uno.dijkstra :as bfs]
    [parte-uno.vertice :as v]
  ))



(defn -main
  "parte 1 del TP de TDA"
  [& args]
  (println 
    (bfs/espias-mas-cerca 
      (g/crear (slurp "mapa.coords")) 
      (v/crear 398 122) 
      [(v/crear 115 273) (v/crear 296 278) (v/crear 227 180)]
    )
  )
)