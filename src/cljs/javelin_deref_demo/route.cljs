(ns javelin-deref-demo.route
  (:require-macros [javelin.core :refer [dosync]])
  (:require
   [secretary.core :refer-macros [defroute]]
   [javelin-deref-demo.app :as a]
   [javelin-deref-demo.nav :as n]
   [javelin-deref-demo.views.home :as vh]
   [javelin-deref-demo.views.view1 :as v1]
   [javelin-deref-demo.views.view2 :as v2]))

(defroute "/" []
  (dosync (reset! a/view (vh/view))
          (reset! a/page-title "Home")))

(defroute "/view1" []
  (reset! a/view (v1/view))
  (reset! a/page-title "View 1"))

(defroute "/view2" []
  (let [v (v2/view)]
    (dosync (reset! a/view v)
            (reset! a/page-title "View 2"))))
