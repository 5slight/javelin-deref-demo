(ns javelin-deref-demo.serve
  (:require [ring.util.response :as res]
            [ring.util.mime-type :as mt]))

(def root "public")

(defn static-file [p]
  (-> (res/file-response p {:root root})
      (res/content-type (mt/ext-mime-type p))))

(defn main [r]
  (let [u (:uri r)
        sf-re #"(?i)^\/.*\.(js|js.map|css|png|jpeg|jpg)$"]
    (static-file (if (re-matches sf-re u)
                   u "index.html"))))
