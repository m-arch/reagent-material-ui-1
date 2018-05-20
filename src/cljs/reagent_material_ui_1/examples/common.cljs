(ns reagent-material-ui-1.examples.common
  (:require [reagent.core :as r]
            [re-material-ui-1.core :as ui]))

(defn code-button [state key]
  [ui/avatar {:class-name "right"}
   [ui/icon-button {:color "default"
                    :on-click #(reset! state (assoc @state key (not (key @state))))}
    [ui/code-icon]]])

(defn drawer-component [name]
  [:a.list-item {:href (str "/examples/" name)}
   [ui/list-item {:button true}
    [ui/list-item-text {:inset true :primary name}]]])
