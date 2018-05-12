(ns parte-uno.problema)


(defprotocol Problema 
    (terminado? [_])
    (conclusion [_])
    (alternativa [_])
)

(defn solucion-greedy [problema]
    (if (terminado? problema)
        (conclusion problema) ;devuelve nil si no hay solucion
        (solucion-greedy (alternativa problema))
    )
)