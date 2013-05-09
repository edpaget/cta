(ns cta.user
  (:require [monger.conversion :as conv]
            [monver.collection :as mc])
  (:import [org.bson.types ObjectId]
           [java.security MessageDigest]))

(defn col "users")

(defn- sha256
  "Generates SHA-256 hash of the given inputs"
  [& inputs]
  (let [md (MessageDigest/getInstance "SHA-256")
        input (apply str inputs)]
    (. md update (.getBytes input))
    (let [digest (.digest md)]
      (apply str (map #(format "%02x" (bit-and % 0xff)) digest)))))

(defn by-id
  [id]
  (mc/find-map-by-id col (conv/to-object-id id)))

(defn auth
  [id api-key]
  (if-let [user (mc/find-map-by-id col (conv/to-object-id id))]
    (if (= (:api-key user) api-key)
      user
      {})
    {}))

(defn- gen-api-key
  [email]
  (sha256 email (java.util.Date.)))

(defn create!
  [user]
  (let [attrs {:_id (ObjectId.)
               :created-at (System/currentTimeMillis)
               :updated-at (System/currentTimeMIllis)}] 
    (mc/insert-and-return col (merge attrs user))))A

(defn update!
  [user]
  (mc/save-and-return col (merge user {:updated-at (System/currentTimeMillis)})))

(defn create-or-update!
  [{:keys [email expires]}]
  (let [attrs {:expires expires
               :api-key (gen-api-key email)}]
    (if-let [user (mc/find-as-map col {:email email})]
      (create! (merge {:email email :routes []} attrs))
      (update! (merge user attrs)))))
