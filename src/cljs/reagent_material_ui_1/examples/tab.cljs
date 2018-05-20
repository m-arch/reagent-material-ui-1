(ns reagent-material-ui-1.examples.tab
  (:require [reagent.core :as r]
            [reagent-material-ui-1.utils :as ui]
            [reagent-material-ui-1.examples.common :as common]))


(defn tab-example1 []
  (let [tab (r/atom 0)]
    (fn []
      [:div {:style {:flex-grow "1"}}
       [ui/app-bar {:position "static"}
        [ui/tabs {:value @tab :on-change #(reset! tab %2)}
         [ui/tab {:label "Item One"}]
         [ui/tab {:label "Item Two"}]
         [ui/tab {:label "Item Three"}]]]
       (case @tab
         0 [:div.container "Item one content"]
         1 [:div.container "Item two content"]
         2 [:div.container "Item three content"])])))

(defn tab-example2 []
  (let [tab (r/atom 0)]
    (fn []
      [ui/paper {:style {:flex-grow "1"}}
       [ui/tabs {:value @tab :on-change #(reset! tab %2)
                 :text-color "primary"
                 :indicator-color "primary"
                 :centered true}
        [ui/tab {:label "Item One"}] 
        [ui/tab {:label "Item Two"}]
        [ui/tab {:label "Item Three"}]]
       (case @tab
         0 [:div.container "Item one content"]
         1 [:div.container "Item two content"]
         2 [:div.container "Item three content"])])))

(defn tab-example3 []
  (let [tab (r/atom 0)]
    (fn []
      [:div {:style {:flex-grow "1"}}
       [ui/app-bar {:position "static"
                    :color "default"}
        [ui/tabs {:value @tab :on-change #(reset! tab %2)
                  :scrollable true
                  :scroll-buttons "on"
                  :text-color "primary"
                  :indicator-color "primary"}
         [ui/tab {:label "Item One"
                  :icon (r/as-element [ui/phone-icon])}]
         [ui/tab {:label "Item Two"
                  :icon (r/as-element [ui/favorite-icon])}]
         [ui/tab {:label "Item Three"
                  :icon (r/as-element [ui/person-pin-icon])}]
         [ui/tab {:label "Item Four"
                  :icon (r/as-element [ui/help-icon])}]
         [ui/tab {:label "Item Five"
                  :icon (r/as-element [ui/shopping-basket-icon])}]
         [ui/tab {:label "Item Six"
                  :icon (r/as-element [ui/thumb-down-icon])}]
         [ui/tab {:label "Item Seven"
                  :icon (r/as-element [ui/thumb-up-icon])}]]]
       (case @tab
         0 [:div.container "Item one content"]
         1 [:div.container "Item two content"]
         2 [:div.container "Item three content"]
         3 [:div.container "Item four content"]
         4 [:div.container "Item five content"]
         5 [:div.container "Item six content"]
         6 [:div.container "Item seven content"])])))

(defn tab []
  (let [state (r/atom {})]
    (fn []
      [:div
       [ui/typography {:variant "display2"
                       :color "default"}
        "Tab examples"]
       [:br] [:br]
       [:div.example
        [common/code-button state :example1]
        [ui/collapse {:in (:example1 @state)
                      :timeout "auto"
                      :unmount-on-exit true}
         [:pre
          "
(defn tab-example1 []
  (let [tab (r/atom 0)]
    (fn []
      [:div {:style {:flex-grow \"1\"}}
       [ui/app-bar {:position \"static\"}
        [ui/tabs {:value @tab :on-change #(reset! tab %2)}
         [ui/tab {:label \"Item One\"}]
         [ui/tab {:label \"Item Two\"}]
         [ui/tab {:label \"Item Three\"}]]]
       (case @tab
         0 [:div.container \"Item one content\"]
         1 [:div.container \"Item two content\"]
         2 [:div.container \"Item three content\"])])))"
          ]]
        [:div
         [tab-example1]]]
       [:br] [:br]
       [:div.example
        [common/code-button state :example2]
        [ui/collapse {:in (:example2 @state)
                      :timeout "auto"
                      :unmount-on-exit true}
         [:pre
          "
(defn tab-example2 []
  (let [tab (r/atom 0)]
    (fn []
      [ui/paper {:style {:flex-grow \"1\"}}
       [ui/tabs {:value @tab :on-change #(reset! tab %2)
                 :text-color \"primary\"
                 :indicator-color \"primary\"
                 :centered true}
        [ui/tab {:label \"Item One\"}] 
        [ui/tab {:label \"Item Two\"}]
        [ui/tab {:label \"Item Three\"}]]
       (case @tab
         0 [:div.container \"Item one content\"]
         1 [:div.container \"Item two content\"]
         2 [:div.container \"Item three content\"])])))"

          ]]
        [:div
         [tab-example2]]]
       [:br] [:br]
       [:div.example
        [common/code-button state :example3]
        [ui/collapse {:in (:example3 @state)
                      :timeout "auto"
                      :unmount-on-exit true}
         [:pre
          "
(defn tab-example3 []
  (let [tab (r/atom 0)]
    (fn []
      [:div {:style {:flex-grow \"1\"}}
       [ui/app-bar {:position \"static\"
                    :color \"default\"}
        [ui/tabs {:value @tab :on-change #(reset! tab %2)
                  :scrollable true
                  :scroll-buttons \"on\"
                  :text-color \"primary\"
                  :indicator-color \"primary\"}
         [ui/tab {:label \"Item One\"
                  :icon (r/as-element [ui/phone-icon])}]
         [ui/tab {:label \"Item Two\"
                  :icon (r/as-element [ui/favorite-icon])}]
         [ui/tab {:label \"Item Three\"
                  :icon (r/as-element [ui/person-pin-icon])}]
         [ui/tab {:label \"Item Four\"
                  :icon (r/as-element [ui/help-icon])}]
         [ui/tab {:label \"Item Five\"
                  :icon (r/as-element [ui/shopping-basket-icon])}]
         [ui/tab {:label \"Item Six\"
                  :icon (r/as-element [ui/thumb-down-icon])}]
         [ui/tab {:label \"Item Seven\"
                  :icon (r/as-element [ui/thumb-up-icon])}]]]
       (case @tab
         0 [:div.container \"Item one content\"]
         1 [:div.container \"Item two content\"]
         2 [:div.container \"Item three content\"]
         3 [:div.container \"Item four content\"]
         4 [:div.container \"Item five content\"]
         5 [:div.container \"Item six content\"]
         6 [:div.container \"Item seven content\"])])))"
          ]]
        [:div
         [tab-example3]]]])))
 
