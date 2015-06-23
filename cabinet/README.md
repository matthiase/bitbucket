# Cabinet

Putting various web frameworks through their paces.

## Endpoints
/status - returns a simple JSON response.


## BENCHMARKS
[siege -c20 -r500 -b URL]

cabinet-ruby:
Sinatra serving up responses behing Thin.
  $ rbenv local 2.0.0-p0
  $ bundle install --path vendor
  $ rbenv rehash
  $ bundle exec rackup
  /status 543.18 req/s 

cabinet-jruby:
Sinatra serving up responses behind Puma.
  $ rbenv local jruby-1.7.3
  $ bundle install --path vendor
  $ rbenv rehash
  $ bundle exec rackup
  /status 1992.03 req/s

cabinet-clojure:
Clojure 1.5 using Compojure.
  $ lein run -m cabinet.web/main
  /status 5555.56 req/s

cabinet-go:
  Go using the built-in http module.
  $ GOMAXPROCS=2 go run server.go
  /status 7142.86 req/s

cabinet-java:
  Drop Wizard running with embedded Jetty.
  $ mvn package
  $ java -jar target/cabinet-1.0-SNAPSHOT.jar server cabinet.yml
  /status 2141.33 req/s


## License
Copyright (C) 2013 Matthias Eder
Distributed under the terms of the MIT License.
