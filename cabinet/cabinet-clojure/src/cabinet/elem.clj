(ns cabinet.elem
  (:refer-clojure :exclude (list get delete)))

(def elems (atom {}))

(defn list [ ]
  @elems)

(defn get [id]
  (or (@elems id)
      (throw (IllegalArgumentException. "Element not found"))))

(defn put [id attrs]
  (if (empty? attrs)
    (throw (IllegalArgumentException. "Attributes may not be empty."))
    (let [new-attrs (merge (@elems id) attrs)]
      (swap! elems assoc id new-attrs)
      new-attrs)))

(defn delete [id]
  (let [old-attrs (get id)]
    (swap! elems dissoc id)
    old-attrs))
