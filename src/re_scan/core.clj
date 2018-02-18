(ns re-scan.core
  (:require
   [taoensso.timbre :refer (refer-timbre)]
   [clojure.core.strint :refer (<<)]
   [clojure.java.shell :refer [sh]]
   [xml-in.core :as xml]
   [clojure.data.xml :as dx]
   [clojure.pprint :refer (pprint)]))

(refer-timbre)

(def version "0.0.1")

(defn- filter-tags [t es]
  (filter (fn [m] (= (get m :tag) t)) es))

(defn- attr [a e]
  (get-in e [:attrs a]))

(defn- host [m]
  (let [name (attr :name (first (xml/find-first m [:host :hostnames])))
        address (attr :addr (first (filter-tags :address (xml/find-all m [:host]))))
        ports (filter-tags :port (xml/find-all m [:host :ports]))]
    {(or name address) ports}))

(defn- hosts [scan]
  (filter-tags :host (xml/find-all scan [:nmaprun])))

(defn open-ports [scan]
  (->> scan hosts (map host)))

(defn nmap [path flags hosts]
  (let [{:keys [exit out err] :as res} (sh "sudo" (<< "~{path}/nmap") flags "-oX" "-" hosts)]
    (if (= 0 exit)
      (dx/parse-str out)
      (throw (ex-info "failed to scan" {:result res :path path :flags flags :hosts hosts})))))
