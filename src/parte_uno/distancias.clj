(ns parte-uno.distancias)

(defprotocol Distancias 
    (distancias-a-traves-de [yo actual vecinos distancias-vecinos])
    (distancias-a-traves-de-uno [yo actual vecino distancia])
    (distancia [yo v])
)
(defrecord asistente-distancias [distancias]
    Distancias
    (distancias-a-traves-de [yo actual vecinos distancias-vecinos]
        (if (empty? vecinos)
            yo
            (distancias-a-traves-de 
                (distancias-a-traves-de-uno yo actual (first vecinos) (first distancias-vecinos))
                actual
                (rest vecinos)
                (rest distancias-vecinos)
            )
        )
    )
    (distancias-a-traves-de-uno [yo actual vecino distancia]
        (asistente-distancias. 
            (assoc distancias vecino
                (if (contains? distancias vecino)
                    (min ( + (get distancias vecino) distancia) (get distancias actual))
                    distancia
                )
            )
        )
    )
    (distancia [yo v]
        (get distancias v)
    )
)

(defn crear-distancias [iniciales]
    (asistente-distancias. iniciales)
)