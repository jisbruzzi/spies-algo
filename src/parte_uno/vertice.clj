(ns parte-uno.vertice)

(defprotocol Vertice (x? [_]) (y? [_]))

(defrecord rVertice [x y]
    Vertice
    (x? [_] x)
    (y? [_] y)
)

(defn crear [x y] (rVertice. x y))
