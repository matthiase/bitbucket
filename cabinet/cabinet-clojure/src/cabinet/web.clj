(ns cabinet.web
  (use ring.adapter.jetty)
  (:use compojure.core)
  (:use ring.middleware.json-params)
  (:require [clj-json.core :as json])
  (:require [cabinet.elem :as elem])
  (:require [cabinet.task :as task])
  (:import org.codehaus.jackson.JsonParseException))

(defn json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string data)})

(defroutes handler
  (GET "/" []
    (json-response {"hello" "stranger"}))

  (PUT "/" [name]
    (json-response {"hello" name}))

  (GET "/status" []
    (json-response {"status" "ok"}))

  (GET "/elems" []
    (json-response (elem/list)))

  (GET "/elems/:id" [id]
    (json-response (elem/get id)))

  (PUT "/elems/:id" [id attrs]
    (json-response (elem/put id attrs)))

  (DELETE "/elems/:id" [id]
    (json-response (elem/delete id)))

  (PUT "/tasks" [data]
    (task/perform data)
    (json-response {"status" "ok"}))
)

(def error-codes
  {:invalid 400
   :not-found 404})

(defn wrap-errors [handler]
  (fn [req]
    (try
      (or (handler req) (json-response {"error" "Resource not found"} 404))
    (catch JsonParseException e
      (json-response {"error" "Malformed JSON."} 400))
    (catch RuntimeException e
      (let [{:keys [type message]} (meta e)]
        (json-response {"error" "Internal server error"} 500))))))


(def app
  (-> handler
    wrap-json-params
    wrap-errors))

(defn main []
  (run-jetty app {:port 8080}))
