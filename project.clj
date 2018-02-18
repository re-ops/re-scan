(defproject re-scan "0.1.0"
  :description "A Clojure-fied Nmap wrapper"
  :url "https://github.com/re-ops/re-scan"
  :license  {:name "Apache License, Version 2.0" :url "http://www.apache.org/licenses/LICENSE-2.0.html"}
  :dependencies [
     [org.clojure/clojure "1.9.0"]
     [org.clojure/core.incubator "0.1.4"]
     [com.rpl/specter "1.0.3"]

     ; logging
     [com.taoensso/timbre "4.10.0"]
     [org.clojure/tools.trace "0.7.9"]

     ; repl
     [io.aviso/pretty "0.1.34"]
     [org.clojure/tools.namespace "0.2.11"]

     ; xml processing
     [tolitius/xml-in "0.1.0"]
     [org.clojure/data.xml "0.0.8"]
   ]

   :plugins [
     [jonase/eastwood "0.2.4"]
     [lein-cljfmt "0.5.6"]
     [lein-ancient "0.6.7" :exclusions [org.clojure/clojure]]
     [lein-tag "0.1.0"]
     [lein-set-version "0.3.0"]]

   :profiles {
     :dev {
       :set-version {
         :updates [
            {:path "src/re_scan/core.clj" :search-regex #"\"\d+\.\d+\.\d+\""}
            {:path "README.md" :search-regex #"\d+\.\d+\.\d+"}
          ]}

     }
    }

   :resource-paths  ["resources"]

   :jvm-opts ^:replace ["-Djava.library.path=/usr/lib:/usr/local/lib"]

   :repositories  {"bintray"  "https://dl.bintray.com/content/narkisr/narkisr-jars"}

   :aliases {
      "travis" [
         "do" "clean," "compile," "cljfmt" "check," "eastwood"
      ]
    }
)
