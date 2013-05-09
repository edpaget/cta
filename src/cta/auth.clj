(ns cta.auth
  (:require [clojure.data.json :as json]
            [sleuth.user :as user]
            [clj-http.client :as client])
  (:use compojure.core))

(defn verify
  [assertion]
  (let [body  (json/write-str {:assertion assertion
                               :audience "http://localhost:3000"})]
    (client/post "https://login.persona.org/verify" 
                 {:body body
                  :headers {"Content-Type" "application/json"}})))

(defn create-or-update!
  [assertion]
  (let [verification (-> (verify assertion)
                         :body
                         (json/read-str :key-fn keyword))]
    (if (= "okay" (:status verification))
      (user/create-or-update! verification)
      (throw (Exception. "Verification Failed")))))

(defroutes auth-routes
  (POST "/login" [assertion] 
        (merge (create-or-update! assertion)
               {:sucesss true}))
  (POST "/logout" [] {:success true}))
