(ns parte-uno.core-test
  (:require [clojure.test :refer :all]
            [parte-uno.core :refer :all]
            [clojure.string :as str]
    [parte-uno.grafo :as g]
    [parte-uno.bfs :as bfs]
    [parte-uno.dijkstra :as djk]
    [parte-uno.vertice :as v]
    [parte-uno.problema :as p]
    [clojure.math.numeric-tower :as math]
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


(deftest arbol
  (def grafo (g/crear "0 0 - 5 0 \n 5 0 - 1 1 \n 5 0 - 2 1 \n 1 1 - 1 2 \n 2 1 - 2 8 \n 2 8 - 2 9 \n 2 9 - 2 10")) 
  ;5 0 es el centro,
  ; 00 está a distancia 5 por un vertice, 
  ;1 2 a distancia raíz de 17 +1 a través de 2, 
  ;2 8 a distancia raíz de 10 +7 a través de 2
  (def analisis-sin-pesos (p/analisis-greedy (bfs/problema-sin-pesos
        grafo
        (v/crear 5 0) 
        [(v/crear 0 0) (v/crear 2 8) (v/crear 1 2)]
      )))
  (def analisis-con-pesos (p/analisis-greedy (djk/problema-con-pesos
        grafo
        (v/crear 5 0) 
        [(v/crear 0 0) (v/crear 2 8) (v/crear 1 2)]
      )))
  (testing "analisis correcto en problema con pesos"
    (is (= (analisis-con-pesos (v/crear 0 0)) 5))
    (is (= (analisis-con-pesos (v/crear 5 0)) 0))
    (is (= (analisis-con-pesos (v/crear 1 1))    (math/sqrt (+ (* 4 4 ) 1) ) ))
    (is (= (analisis-con-pesos (v/crear 1 2)) (+ (math/sqrt (+ (* 4 4 ) 1) ) 1)  ))
    (is (= (analisis-con-pesos (v/crear 2 1))    (math/sqrt (+ (* 3 3 ) 1) )  ))
    (is (= (analisis-con-pesos (v/crear 2 8)) (+ (math/sqrt (+ (* 3 3 ) 1) ) 7)  ))
    (is (= (analisis-con-pesos (v/crear 2 9)) (+ (math/sqrt (+ (* 3 3 ) 1) ) 8)  )); este no es necesario calcularlo pero siempre se calcula uno de más
    (is (= (analisis-con-pesos (v/crear 2 10)) nil)); este no es necesario calcularlo
  )
  (testing "analisis correcto en problema sin pesos"
    (is (= (analisis-sin-pesos (v/crear 0 0)) 1))
    (is (= (analisis-sin-pesos (v/crear 5 0)) 0))
    (is (= (analisis-sin-pesos (v/crear 1 1)) 1))
    (is (= (analisis-sin-pesos (v/crear 1 2)) 2))
    (is (= (analisis-sin-pesos (v/crear 2 1)) 1))
    (is (= (analisis-sin-pesos (v/crear 2 8)) 2))
    (is (= (analisis-sin-pesos (v/crear 2 9)) 3)); este no es necesario calcularlo pero siempre se calcula uno de más
    (is (= (analisis-sin-pesos (v/crear 2 10)) nil)); este no es necesario calcularlo
  )

  (testing "determinacion correcta en problema con pesos"
    (is (= 
      (p/camino-desde
        analisis-con-pesos
        grafo
        (v/crear 2 8)
      )
      (list
        (v/crear 2 8)
        (v/crear 2 1)
        (v/crear 5 0)
      )
    ))
  )

  
  (testing "determinacion correcta en problema sin pesos"
    (is (= 
      (p/camino-desde
        analisis-con-pesos
        grafo
        (v/crear 2 8)
      )
      (list
        (v/crear 2 8)
        (v/crear 2 1)
        (v/crear 5 0)
      )
    ))
  )

  (testing "determinacion correcta del ganador en problema sin pesos (distancias distintas) 1"
    (is (= 
      (p/conserva-documentos analisis-sin-pesos (v/crear 0 0) (v/crear 1 1))
      true
    ))
  )

  (testing "determinacion correcta del ganador en problema sin pesos (distancias distintas) 2"
    (is (= 
      (p/conserva-documentos analisis-sin-pesos (v/crear 2 8) (v/crear 0 0))
      false
    ))
  )

  (testing "determinacion correcta del ganador en problema sin pesos (distancias iguales)"
    (is (= 
      (p/conserva-documentos analisis-sin-pesos (v/crear 0 0) (v/crear 0 0))
      true
    ))
  )

  (testing "determinacion correcta del ganador en problema con pesos (distancias distintas) 1"
    (is (= 
      (p/conserva-documentos analisis-con-pesos (v/crear 0 0) (v/crear 1 1))
      false
    ))
  )

  (testing "determinacion correcta del ganador en problema con pesos (distancias distintas) 2"
    (is (= 
      (p/conserva-documentos analisis-con-pesos (v/crear 2 8) (v/crear 0 0))
      false
    ))
  )

  (testing "determinacion correcta del ganador en problema con pesos (distancias iguales)"
    (is (= 
      (p/conserva-documentos analisis-con-pesos (v/crear 0 0) (v/crear 0 0))
      true
    ))
  )

)




(deftest ciclo
  (def grafo (g/crear "0 0 - 0 1 \n 0 1 - 0 2 \n 0 2 - 0 3 \n 0 3 - 0 4 \n 0 0 - 5 2 \n 0 4 - 5 2"))
  (def analisis-sin-pesos (p/analisis-greedy (bfs/problema-sin-pesos
        grafo
        (v/crear 0 0) 
        [(v/crear 0 3) (v/crear 0 4)]
      )))
  (def analisis-con-pesos (p/analisis-greedy (djk/problema-con-pesos
        grafo
        (v/crear 0 0) 
        [(v/crear 0 3) (v/crear 0 4)]
      )))
  (testing "analisis correcto en problema con pesos"
    (is (= (analisis-con-pesos (v/crear 0 0)) 0))
    (is (= (analisis-con-pesos (v/crear 0 1)) 1))
    (is (= (analisis-con-pesos (v/crear 0 2)) 2))
    (is (= (analisis-con-pesos (v/crear 0 3)) 3))
    (is (= (analisis-con-pesos (v/crear 0 4)) 4))
    (is (= (analisis-con-pesos (v/crear 5 2)) (math/sqrt (+ (* 5 5) (* 2 2) ) )))
  )
  (testing "analisis correcto en problema sin pesos"
    (is (= (analisis-sin-pesos (v/crear 0 0)) 0))
    (is (= (analisis-sin-pesos (v/crear 0 1)) 1))
    (is (= (analisis-sin-pesos (v/crear 0 2)) 2))
    (is (= (analisis-sin-pesos (v/crear 0 3)) 3))
    (is (= (analisis-sin-pesos (v/crear 0 4)) 2))
    (is (= (analisis-sin-pesos (v/crear 5 2)) 1))
  )

  (testing "determinacion correcta en problema con pesos"
    (is (= 
      (p/camino-desde
        analisis-con-pesos
        grafo
        (v/crear 0 4)
      )
      (list
        (v/crear 0 4)
        (v/crear 0 3)
        (v/crear 0 2)
        (v/crear 0 1)
        (v/crear 0 0)
      )
    ))
  )

  
  (testing "determinacion correcta en problema sin pesos"
    (is (= 
      (p/camino-desde
        analisis-sin-pesos
        grafo
        (v/crear 0 4)
      )
      (list
        (v/crear 0 4)
        (v/crear 5 2)
        (v/crear 0 0)
      )
    ))
  )
)
