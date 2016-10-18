(ns javelin-deref-demo.app
  (:require-macros [javelin.core :refer [defc cell= defc=]])
  (:require [javelin.core]))

(defc previous-path "")
(defc path "")
(defc page-title "")
(defc view nil)
