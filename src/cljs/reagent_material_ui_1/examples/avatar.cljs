(ns reagent-material-ui-1.examples.avatar
  (:require [reagent.core :as r]
            [re-material-ui-1.core :as ui]
            [reagent-material-ui-1.examples.common :as common]))

(defn avatar []
  (let [state (r/atom {})]
    (fn []
      [:div
       [ui/typography {:variant "display2"
                       :color "default"}
        "Avatar examples"]
       [:br] [:br]
       [:div.example
        [common/code-button state :example1]
        [ui/collapse {:in (:example1 @state)
                      :timeout "auto"
                      :unmount-on-exit true}
         [:pre
          "
(defn avatar []
  [:div {:style {:display \"flex\"
                 :justify-content \"center\"}}
   [ui/avatar {:alt \"John Smith\"
               :src \"/images/john-smith.jpg\"
               :style {:margin \"10px\"}}]
   [ui/avatar {:style {:background-color ui/red500
                       :margin \"10px\"}}
    [ui/folder-icon]]
   [ui/avatar {:style {:margin \"10px\"
                       :background-color ui/deep-purple500}}
    \"M\"]])"
          ]]
        [:div {:style {:display "flex"
                       :justify-content "center"}}
         [ui/avatar {:alt "John Smith"
                     :src "/images/john-smith.jpg"
                     :style {:margin "10px"}}]
         [ui/avatar {:style {:background-color ui/red500
                             :margin "10px"}}
          [ui/folder-icon]]
         [ui/avatar {:style {:margin "10px"
                             :background-color ui/deep-purple500}}
          "M"]]]]))) 
