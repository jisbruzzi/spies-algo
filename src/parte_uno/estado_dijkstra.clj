(ns parte-uno.estado-dijkstra)

(defprotocol Distancias 
    (distancias-a-traves-de [yo actual vecinos distancias-vecinos])
    (distancias-a-traves-de-uno [yo actual vecino distancia])
    (siguiente-a-visitar [_])
    (sin-siguiente-a-visitar [_])
    (con-vertice [yo v d])
    (sin-vertice [yo v])
)


(defrecord estado-dijkstra [distancias visitables]
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
        (let [
            distancia-actual (get distancias actual)
            distancia-vecino (or (get distancias vecino) (/ 1.0 0.0) )
            sin-vecino (sin-vertice yo vecino)
        ]
            (con-vertice sin-vecino vecino (min (+ distancia distancia-actual) distancia-vecino))
        )
    )
    (con-vertice [yo v d]
        (estado-dijkstra.
            (assoc distancias v d)
            (let [
                visitables-a-distancia-d (get visitables d)
                visitables-a-distancia-d-nuevos (conj (or visitables-a-distancia-d (list)) v)
            ]
                (assoc visitables d visitables-a-distancia-d-nuevos)
            )
        )
    )

    (sin-vertice [yo v]
        (if (contains? distancias v)
            (let [
                distancia-v (get distancias v)
                vs-a-distancia-d (get visitables distancia-v)
                nuevos-vs-a-distancia-d (rest vs-a-distancia-d)
            ]

                (if (empty? nuevos-vs-a-distancia-d)

                    (estado-dijkstra.
                        distancias
                        (dissoc visitables distancia-v)
                    )

                    (estado-dijkstra.
                        distancias
                        (assoc visitables distancia-v nuevos-vs-a-distancia-d)
                    )
                )
                
            )
            yo
        )
        
    )

    (siguiente-a-visitar [yo]
        (first (nth (first visitables) 1))
    )

    (sin-siguiente-a-visitar [yo]
        (sin-vertice yo (siguiente-a-visitar yo))
    )
)

(defn crear-distancias [inicio]
    (con-vertice (estado-dijkstra. {} (sorted-map)) inicio 0)
)