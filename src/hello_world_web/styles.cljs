(ns hello-world-web.styles)

(def styles
  {:hello
    {:font-size "50px"}
   :border
    (fn [size]
      {:border (str size " solid black")})})
