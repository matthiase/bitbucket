(ns cabinet.task )

(defn perform [data]
  (future
    (Thread/sleep 10000)
    (println "cabinet.task/perform")))
