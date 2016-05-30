(ns postal-messenger.app.core
  (:require-macros [rum.core :refer [defc]])
  (:require [postal-messenger.app.support :as support]
            [rum.core :as rum]
            [griebenschmalz.core :as g]))

(set! js/React (js/require "react-native/Libraries/react-native/react-native.js"))
(defonce react (js/require "react-native/Libraries/react-native/react-native.js"))

(defn create-element [rn-comp opts & children]
  (apply js/React.createElement rn-comp (clj->js opts) children))

(def app-registry (.-AppRegistry react))
(def view (partial create-element (.-View react)))
(def text (partial create-element (.-Text react)))

(defn alert [title]
  (.alert (.-Alert react) title))

(defonce app-state (atom {:greeting "Hello Clojure in iOS and Android"}))
#_(def initial-state {:greeting "Hello world"})

(defc AppRoot
      [state]
      (view {:style {:flexDirection "column" :margin 40 :alignItems "center"}}
            (text {:style {:fontSize 30 :fontWeight "100" :marginBottom 20 :textAlign "center"}} "CHANGE THIS: HELLO WORLD!"))
      #_(view {:style {:flexDirection "column" :margin 40 :alignItems "center"}}
              #_(text {:style {:fontSize 30 :fontWeight "100" :marginBottom 20 :textAlign "center"}} (:greeting @state))
              #_(image {:source logo-img
                        :style  {:width 80 :height 80 :marginBottom 30}})
              [text {:style {:margin-top 22, :margin-left 8}} "CHANGE THIS: HELLO WORLD123"]
              #_(touchable-highlight {:style   {:backgroundColor "#999" :padding 10 :borderRadius 5}
                                      :onPress #(alert "HELLO!!!!!!!!!")}
                                     (text {:style {:color "white" :textAlign "center" :fontWeight "bold"}} "press me"))))

(defonce root-component-factory (support/make-root-component-factory))

(defn render-fn
  [state]
  (support/mount (AppRoot state)))

(defn mount-app [] (support/mount (AppRoot app-state)))

(defn ^:export main
  []
  (enable-console-print!)
  (js/console.log "MAIN")
  (.registerComponent (.-AppRegistry react)
                      "PostalMessenger"
                      (fn [] root-component-factory))
  (mount-app))

(defn on-js-reload
  []
  (js/console.log "JS RELOADING")
  (mount-app))
