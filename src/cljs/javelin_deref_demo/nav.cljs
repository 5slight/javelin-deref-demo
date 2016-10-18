(ns javelin-deref-demo.nav
  (:require
   [clojure.string :as s]
   [goog.events :as events]
   [goog.history.EventType :as EventType]
   [goog.string :as gstring]
   [secretary.core :as secretary]
   [javelin-deref-demo.app :as a])
  (:import
   [goog History]
   [goog.history Html5History]
   [goog Uri]))

(def history (if (.isSupported Html5History)
               (doto (Html5History.)
                 (.setUseFragment false)
                 (.setPathPrefix ""))
               (History.)))

(defn push [path title]
  (let [previous-path (.getPath (.parse Uri (.-location js/window)))]
    (reset! a/path path)
    (reset! a/page-title title)
    (.setToken history path title)
    (if (= path previous-path) ; We dispatch here as well as .setToken won't generate an event in this case
      (secretary/dispatch! path))))

(defn setup-navigation []
  (.setEnabled history true)
  (events/listen
   js/document "click"
   (fn [e]
     ;; If target is A then great
     ;; If target is I then get parent A and substitute that for the target
     ;; (.log js/console "Click target:" (.-target e))
     (if-let [target (cond (= (.. e -target -tagName) "A")
                           (.-target e)
                           (= (.. e -target -tagName) "I")
                           (let [parent (.. e -target -parentElement)]
                             (if (= (.-tagName parent) "A") parent false))
                           :else false)]
       (let [href (.-href target)
             path (.getPath (.parse Uri href))
             title (.-title target)]
         (when-let [route (secretary/locate-route path)]
           (push path title)
           (.preventDefault e))))))

  (events/listen
   history EventType/NAVIGATE
   (fn [e]
     (print "Nav event:" (.-token e))
     (secretary/dispatch! (.-token e))))
  (push (.getPath (.parse Uri (.-location js/window))) nil))

(defn is-in-lab? []
  (let [location (.-location js/window)
        uri (.getPath (.parse Uri location))
        seg (first (rest (s/split uri #"/")))]
    (= "lab" seg)))
