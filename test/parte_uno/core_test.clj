(ns parte-uno.core-test
  (:require [clojure.test :refer :all]
            [parte-uno.core :refer :all]
            [clojure.string :as str]
    [parte-uno.grafo :as g]
    [parte-uno.bfs :as bfs]
    [parte-uno.dijkstra :as djk]
    [parte-uno.vertice :as v]
    [parte-uno.problema :as p]
    ))

(deftest lineal
  (def grafo (g/crear "0 0 - 1 0 \n 1 0 - 1 1 \n 1 1 - 0 1"))
  (def analisis-sin-pesos (p/analisis-greedy (bfs/problema-sin-pesos
        grafo
        (v/crear 0 0) 
        [(v/crear 0 1) (v/crear 1 1)]
      )))
  (def analisis-con-pesos (p/analisis-greedy (djk/problema-con-pesos
        grafo
        (v/crear 0 0) 
        [(v/crear 0 1) (v/crear 1 1)]
      )))
  (testing "analisis correcto en problema sin pesos"
    (is (= 
      analisis-sin-pesos
      {
        (v/crear 0 0) 0,
        (v/crear 0 1) 3,
        (v/crear 1 0) 1,
        (v/crear 1 1) 2,
      }
    ))
  )
  (testing "analisis correcto en problema con pesos"
    (is (= 
      analisis-con-pesos
      {
        (v/crear 0 0) 0,
        (v/crear 0 1) 3,
        (v/crear 1 0) 1,
        (v/crear 1 1) 2,
      }
    ))
  )

  (testing "determinacion correcta en problema con pesos"
    (is (= 
      (p/camino-desde
        analisis-con-pesos
        grafo
        (v/crear 1 1)
      )
      (list
        (v/crear 1 1)
        (v/crear 1 0)
        (v/crear 0 0)
      )
    ))
  )

  (testing "determinacion correcta en problema sin pesos"
    (is (= 
      (p/camino-desde
        analisis-sin-pesos
        grafo
        (v/crear 1 1)
      )
      (list
        (v/crear 1 1)
        (v/crear 1 0)
        (v/crear 0 0)
      )
    ))
  )

  (testing "determinacion correcta del ganador en problema sin pesos (distancias distintas) 1"
    (is (= 
      (p/conserva-documentos analisis-sin-pesos (v/crear 1 0) (v/crear 1 1))
      true
    ))
  )

  (testing "determinacion correcta del ganador en problema sin pesos (distancias distintas) 2"
    (is (= 
      (p/conserva-documentos analisis-sin-pesos (v/crear 0 1) (v/crear 1 1))
      false
    ))
  )

  (testing "determinacion correcta del ganador en problema sin pesos (distancias iguales)"
    (is (= 
      (p/conserva-documentos analisis-sin-pesos (v/crear 1 0) (v/crear 1 0))
      true
    ))
  )

  (testing "determinacion correcta del ganador en problema con pesos (distancias distintas) 1"
    (is (= 
      (p/conserva-documentos analisis-con-pesos (v/crear 1 0) (v/crear 1 1))
      true
    ))
  )

  (testing "determinacion correcta del ganador en problema con pesos (distancias distintas) 2"
    (is (= 
      (p/conserva-documentos analisis-con-pesos (v/crear 0 1) (v/crear 1 1))
      false
    ))
  )

  (testing "determinacion correcta del ganador en problema con pesos (distancias iguales)"
    (is (= 
      (p/conserva-documentos analisis-con-pesos (v/crear 1 0) (v/crear 1 0))
      true
    ))
  )

)
