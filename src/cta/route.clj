(ns cta.route
  (:use compojure.core
        monger.operators)
  (:require [monger.collection :as mc]
            [monger.conversion :as conv]))

(def col "users")

(defn by-id
  [user-id id]
  (mc/find-map-by-id col user-id)
  )
