(ns parte-uno.core
  (:gen-class)
  (:require 
    [clojure.string :as str]
    [parte-uno.arista :as a]
  ))

(defn hacer-grafo []
  (map a/crear
      (str/split-lines 
          (slurp "mapa.coords")
      )
  )
)



(defn -main
  "parte 1 del TP de TDA"
  [& args]
  (println (hacer-grafo))
  )