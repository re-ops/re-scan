(ns re-scan.core
  (:require
   [taoensso.timbre :refer (refer-timbre)]
   [clojure.core.strint :refer (<<)]
   [clojure.java.shell :refer [sh]]
   [xml-in.core :as xml]
   [clojure.data.xml :as dx]
   [clojure.pprint :refer (pprint)]))

(refer-timbre)

(defn- filter-tags [t es]
  (filter (fn [m] (= (get m :tag) t)) es))

(defn- attr [a e]
  (get-in e [:attrs a]))

(defn- port [{:keys [attrs content]}]
  (apply merge attrs (map :attrs content)))

(defn- host-ports [m]
  (let [name (attr :name (first (xml/find-first m [:host :hostnames])))
        address (attr :addr (first (filter-tags :address (xml/find-all m [:host]))))
        ports (map port (filter-tags :port (xml/find-all m [:host :ports])))]
    {(or name address) ports}))

(defn- hosts [scan]
  (filter-tags :host (xml/find-all scan [:nmaprun])))

(defn into-ports
  "Convert scan result into host -> open ports mapping using a -T5 scan"
  [scan]
  (->> scan hosts (map host-ports)))

(defn- host-addresses [m]
  (let [name (attr :name (first (xml/find-first m [:host :hostnames])))
        address (attr :addr (first (filter-tags :address (xml/find-all m [:host]))))
        addresses (map :attrs (filter-tags :address (xml/find-all m [:host])))]
    {(or name address) addresses}))

(defn into-hosts
  "Convert scan result into a host -> addresses mapping using a -sP scan"
  [scan]
  (-> scan hosts (map host-addresses)))

(defn nmap [path flags hosts]
  (let [{:keys [exit out err] :as res} (sh "sudo" (<< "~{path}/nmap") flags "-oX" "-" hosts)]
    (if (= 0 exit)
      (dx/parse-str out)
      (throw (ex-info "failed to scan" {:result res :path path :flags flags :hosts hosts})))))

(comment
  (def scan (nmap "/usr/bin/" "-sP" "192.168.1.0/24"))
  (pprint (->> scan hosts (map into-hosts))))
