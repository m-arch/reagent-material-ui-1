(ns reagent-material-ui-1.examples.app-bar
  (:require [reagent.core :as r]
            [reagent-material-ui-1.utils :as ui]
            [reagent-material-ui-1.examples.common :as common]))

(defn app-bar-example3 []
  (let [state (r/atom {:login true})]
    (fn []
      [:div {:style {:flex-grow "1"}}
       [ui/form-group
        [ui/form-control-label {:label (if (:login @state)
                                         "Logout"
                                         "Login")
                                :control (r/create-element (aget js/MaterialUI (name :Switch))
                                                           #js{:checked (:login @state)
                                                               :onChange #(swap! state assoc
                                                                                 :login (not (:login @state)))})}]]
       [ui/app-bar {:position "static"}
        [ui/toolbar
           [ui/icon-button {:color "inherit"
                            :style {:margin-left "-12px"
                                    :margin-right "20px"}}
            [ui/menu-icon]]
           [ui/typography {:variant "title" :color "inherit"
                           :style {:flex "1"}}
            "Title"]
         (when (:login @state)
           [:div
            [ui/icon-button {:color "inherit"
                             :on-click #(swap! state assoc :menu-bar (. % -target))}
             [ui/account-circle-icon]]
            [ui/menu {:id "menu-appbar"
                      :anchor-el (:menu-bar @state)
                      :anchor-origin {:vertical "top" :horizontal "right"}
                      :transform-origin {:veritcal "top" :horizontal "right"}
                      :open (if (:menu-bar @state) true false)
                      :on-close #(swap! state assoc :menu-bar false)}
             [ui/menu-item {:on-click #(swap! state assoc :menu-bar false)}
              "Profile"]
             [ui/menu-item {:on-click #(swap! state assoc :menu-bar false)}
              "My Account"]]])]]])))

(defn app-bar []
  (let [state (r/atom {})]
    (fn []
      [:div
       [ui/typography {:variant "display2"
                       :color "default"}
        "App Bar examples"]
       [:br] [:br]
       [:div.example
        [common/code-button state :example1]
        [ui/collapse {:in (:example1 @state)
                      :timeout "auto"
                      :unmount-on-exit true}
         [:pre
          "
(defn app-bar []
  [:div {:style {:flex-grow \"1\"}}
   [ui/app-bar {:position \"static\" :color \"default\"}
    [ui/toolbar
     [ui/typography {:variant \"title\" :color \"inherit\"}
      \"Title\"]]]]
"]]
        [:div {:style {:flex-grow "1"}}
         [ui/app-bar {:position "static" :color "default"}
          [ui/toolbar
           [ui/typography {:variant "title" :color "inherit"}
            "Title"]]]]]
       [:br] [:br]
       [:div.example
        [common/code-button state :example2]
        [ui/collapse {:in (:example2 @state)
                      :timeout "auto"
                      :unmount-on-exit true}
         [:pre
          "
(defn app-bar []
  [:div {:style {:flex-grow \"1\"}}
   [ui/app-bar {:position \"static\"}
    [ui/toolbar
     [ui/icon-button {:color \"inherit\"
                      :style {:margin-left \"-12px\"
                              :margin-right \"20px\"}}
      [ui/menu-icon]]
     [ui/typography {:variant \"title\" :color \"inherit\"
                     :style {:flex \"1\"}}
      \"Title\"]
     [ui/button {:color \"inherit\"}
      \"Login\"]]]])
   "]]
        [:div {:style {:flex-grow "1"}}
         [ui/app-bar {:position "static"}
          [ui/toolbar
           [ui/icon-button {:color "inherit"
                            :style {:margin-left "-12px"
                                    :margin-right "20px"}}
            [ui/menu-icon]]
           [ui/typography {:variant "title" :color "inherit"
                           :style {:flex "1"}}
            "Title"]
           [ui/button {:color "inherit"}
            "Login"]]]]]
       [:br] [:br]
       [:div.example
        [common/code-button state :example3]
        [ui/collapse {:in (:example3 @state)
                      :timeout "auto"
                      :unmount-on-exit true}
         [:pre
          "
(defn app-bar-example3 []
  (let [state (r/atom {:login true})]
    (fn []
      [:div {:style {:flex-grow \"1\"}}
       [ui/form-group
        [ui/form-control-label {:label (if (:login @state)
                                         \"Logout\"
                                         \"Login\")
                                :control (r/create-element (aget js/MaterialUI (name :Switch))
                                                           #js{:checked (:login @state)
                                                               :onChange #(swap! state assoc
                                                                                 :login (not (:login @state)))})}]]
       [ui/app-bar {:position \"static\"}
        [ui/toolbar
           [ui/icon-button {:color \"inherit\"
                            :style {:margin-left \"-12px\"
                                    :margin-right \"20px\"}}
            [ui/menu-icon]]
           [ui/typography {:variant \"title\" :color \"inherit\"
                           :style {:flex \"1\"}}
            \"Title\"]
         (when (:login @state)
           [:div
            [ui/icon-button {:color \"inherit\"
                             :on-click #(swap! state assoc :menu-bar (. % -target))}
             [ui/account-circle-icon]]
            [ui/menu {:id \"menu-appbar\"
                      :anchor-el (:menu-bar @state)
                      :anchor-origin {:vertical \"top\" :horizontal \"right\"}
                      :transform-origin {:veritcal \"top\" :horizontal \"right\"}
                      :open (if (:menu-bar @state) true false)
                      :on-close #(swap! state assoc :menu-bar false)}
             [ui/menu-item {:on-click #(swap! state assoc :menu-bar false)}
              \"Profile\"]
             [ui/menu-item {:on-click #(swap! state assoc :menu-bar false)}
              \"My Account\"]]])]]])))"
          ]]
        [:div {:style {:flex-grow "1"}}
         [app-bar-example3]]]])))
