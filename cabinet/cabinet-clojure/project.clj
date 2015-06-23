(defproject cabinet "0.0.1"
  :description "A simple REST datastore interface."
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [ring/ring-jetty-adapter "1.1.1"]
                 [compojure "1.1.1"]
                 [ring-json-params "0.1.3"]
                 [clj-json "0.5.1"]]
  ;;:dev-dependencies [[lein-run "1.0.0"]]
  :main cabinet.web
)
