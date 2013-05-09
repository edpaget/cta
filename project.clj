(defproject cta "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [clj-http "0.7.2"]
                 [ring/ring-json "0.2.0"]
                 [com.novemberain/monger "1.5.0"]
                 [org.clojure/data.json "0.2.2"]
                 [prismatic/dommy "0.1.1-SNAPSHOT"]]
  :plugins [[lein-cljsbuild "0.3.0"]
            [lein-ring "0.8.3"]]
  :ring {:handler cta.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.3"]]}}
  :cljsbuild {
              :builds [{
                        :source-paths ["src-cljs"]
                        :compiler {
                                   :output-to "resources/public/javascripts/app.js"
                                   :optimizations :whitespace
                                   :pretty-print true}}]})
