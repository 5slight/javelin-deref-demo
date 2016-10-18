#!/usr/bin/env boot

(set-env!
 :project      'javelin-deref-demo
 :version      "0.0.1"
 :dependencies '[[adzerk/boot-cljs            "1.7.228-1"]
                 [adzerk/boot-reload          "0.4.8"]
                 [hoplon                      "6.0.0-alpha16"]
                 [hoplon/boot-hoplon          "0.2.4"]
                 [org.clojure/clojurescript   "1.7.228"]
                 [org.clojure/clojure         "1.8.0"]
                 [secretary                   "1.2.3"]
                 [io.vertx/clojure-api        "1.0.4"]

                 [pandeiro/boot-http          "0.7.3"]
                 [ring/ring-core              "1.5.0"]]
 :source-paths #{"src/cljs" "src/hl" "serve"}
 :asset-paths  #{"assets"})

(require
 '[adzerk.boot-cljs         :refer [cljs]]
 '[adzerk.boot-reload       :refer [reload]]
 '[hoplon.boot-hoplon       :refer [hoplon]]
 '[pandeiro.boot-http       :refer [serve]])

(deftask dev
  "Build for development with source maps."
  []
  (comp
   (serve
        :port    8000
        :reload  true
        :handler `javelin-deref-demo.serve/main)
   (watch)
   (speak)
   (reload)
   (hoplon :pretty-print true)
   (cljs :source-map true)
   (target :dir #{"public"})))
