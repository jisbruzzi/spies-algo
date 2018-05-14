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
    
      (p/analisis-greedy (djk/problema-con-pesos ;bfs/problema-sin-pesos
        (g/crear (slurp "mapa.coords")) 
        (v/crear 398 122) 
        [(v/crear 115 273) (v/crear 296 278) (v/crear 227 180)]
      ))
    
  )
)

(defn averiguar [que de-quien]
  (let [
    aviso (println "Ingresar " que " del " de-quien)
    entrada (read-line)
    es-entero (re-matches #"[0-9]+" entrada)
    salida (if es-entero
      (Integer. entrada)
      (let [
        repregunta (println "Ese no es un numero natural!")
      ]
        (averiguar que de-quien)
      )
    )
  ]
    salida
  )

)

(defn tp [funcion-creadora]
  (let [
    x-con (averiguar "x" "espia con los documentos")
    y-con (averiguar "y" "espia con los documentos")
    v-con (v/crear x-con y-con)

    x-sin (averiguar "x" "espia sin los documentos")
    y-sin (averiguar "y" "espia sin los documentos")
    v-sin (v/crear x-sin y-sin)

    x-aer (averiguar "x" "aeropuerto")
    y-aer (averiguar "y" "aeropuerto")
    v-aer (v/crear x-sin y-sin)

    grafo (g/crear (slurp "mapa.coords"))
    distancias (p/analisis-greedy (funcion-creadora
      grafo
      v-aer
      [v-con v-sin]
    ))
    conservados (p/conserva-documentos
      distancias
      v-con
      v-sin
    )
    oc (if conservados
      (println "El espia que tenia los documentos los conserva!")
      (println "El espia que queria los documentos los roba!")
    )

    c-con (p/camino-desde distancias grafo v-con)
    o-con (println "El camino desde el espia con los documentos al aeropuerto es:")
    o-con (println c-con)

    c-sin (p/camino-desde distancias grafo v-sin)
    o-sin (println "El camino desde el espia sin los documentos al aeropuerto es:")
    o-sin (println c-sin)
  ]
    true
  )
)

(defn bfs
  "Parte 1 del TP con bfs"
  [& args]
  (let[
    x (println "ejecutando algoritmo similar a bfs")
  ]
    (tp bfs/problema-sin-pesos)
  )

  
)

(defn dijkstra
  "Parte 1 del TP con dijkstra"
  [& args]
  (let[
    x (println "ejecutando algoritmo similar a dijkstra")
  ]
    (tp djk/problema-con-pesos)
  )
)