(ns reagent-material-ui-1.core
    (:require [reagent.core :as reagent :refer [atom]]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]
              [reagent-material-ui-1.main :refer [top-bar drawer home-page
                                                  about-page]]
              [reagent-material-ui-1.examples.app-bar :refer [app-bar]]
              [reagent-material-ui-1.examples.avatar :refer [avatar]]
              [reagent-material-ui-1.examples.drag-drop :refer [drag-drop]]
              [reagent-material-ui-1.examples.pickers :refer [pickers]]
              [reagent-material-ui-1.examples.tab :refer [tab]]
              [reagent-material-ui-1.examples.table :refer [table]]
              [reagent-material-ui-1.examples.text-field :refer [text-field]]
              [re-material-ui-1.core :as ui]))



(defonce page (atom #'home-page))

(defn current-page []
  (let [state (atom {:anchor "left" :drawer-open false
                     :components-section-open false})]
    (fn []
      [ui/mui-theme-provider {:theme (ui/create-mui-theme-fn (clj->js {:palette {:type "light"}}))}
       [:div {:style {:flex-grow "1"}}
        [top-bar state]
        [:div.content.content-shift {:class (if (:drawer-open @state)
                                              (str "content-" (:anchor @state))
                                              (str "contentShift-" (:anchor @state)))}
         [@page]]
        [drawer state]]])))


;; -------------------------
;; Routes

(secretary/defroute "/" []
  (reset! page #'home-page))
(secretary/defroute "/about" []
  (reset! page #'about-page))
(secretary/defroute "/examples/app-bar" []
  (reset! page #'app-bar))
(secretary/defroute "/examples/avatar" []
  (reset! page #'avatar))
(secretary/defroute "/examples/drag-drop" []
  (reset! page #'drag-drop))
(secretary/defroute "/examples/pickers" []
  (reset! page #'pickers))
(secretary/defroute "/examples/tab" []
  (reset! page #'tab))
(secretary/defroute "/examples/table" []
  (reset! page #'table))
(secretary/defroute "/examples/text-field" []
  (reset! page #'text-field))


;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (accountant/configure-navigation!
    {:nav-handler
     (fn [path]
       (secretary/dispatch! path))
     :path-exists?
     (fn [path]
       (secretary/locate-route path))})
  (accountant/dispatch-current!)
  (mount-root))
