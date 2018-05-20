(ns reagent-material-ui-1.main
  (:require [reagent.core :as r]
            [reagent-material-ui-1.utils :as ui]
            [reagent-material-ui-1.examples.common :as common]))


(defn top-bar [state]
  [ui/app-bar {:class-name  (when (:drawer-open @state)
                              (str "appBarShift-" (:anchor @state)))}
   [ui/toolbar {:disable-gutters (not (:drawer-open @state))}
    [ui/icon-button {:color "inherit"
                     :aria-label "open drawer"
                     :on-click #(reset! state (assoc @state :drawer-open true))
                     :style {:margin-right "20px"}}
     [ui/menu-icon]]
    [ui/typography {:style {:flext "1"}
                    :variant "title" :color "inherit" :no-wrap true}
     "reagent-material-ui-1"]]])

(defn drawer [state]
  [ui/drawer {:variant "persistent"
              :anchor (:anchor @state)
              :open (:drawer-open @state)}
   [:div.drawer
     [ui/icon-button {:on-click #(reset! state (assoc @state :drawer-open false))}
      (if (= (:anchor @state) "left")
        [ui/chevron-left-icon]
        [ui/chevron-right-icon])]
     [ui/divider]
     [ui/list-component {:component "nav"}
      [ui/list-item {:button true
                     :on-click #(reset! state
                                        (assoc @state
                                               :components-section-open
                                               (not (:components-section-open @state))))}
       [ui/list-item-text {:inset false :primary  "Components"
                           :class-name "list-section"}]]
      [ui/collapse {:in (:components-section-open @state)
                    :timeout "auto"
                    :unmount-on-exit true}
       [ui/list-component {:component "div"
                           :disable-padding true}
        [common/drawer-component "app-bar"]
        [common/drawer-component "avatar"]
        [common/drawer-component "tab"]
        [common/drawer-component "table"]
        [common/drawer-component "text-field"]]]]]])

(defn home-page []
  )

(defn about-page []
  )
