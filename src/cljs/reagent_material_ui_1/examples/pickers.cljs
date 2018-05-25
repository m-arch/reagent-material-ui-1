(ns reagent-material-ui-1.examples.pickers
  (:require [reagent.core :as r]
            [re-material-ui-1.core :as ui]
            [re-material-ui-1.custom :as custom-ui]
            [reagent-material-ui-1.examples.common :as common]))


(defn pickers-example []
  (let [data (r/atom {:time "15:34"
                      :datetime "2018-05-24T10:30"})]
    [:div {:style {:display "flex" :flex-wrap "wrap"}}
     [custom-ui/input-field :date data :label "Birthday" :type "date" :shrink true
      :style {:margin-left "12px" :margin-right "12px" :width "260px"}]
     [custom-ui/input-field :time data :label "Alarm" :type "time" :shrink true
      :style {:margin-left "12px" :margin-right "12px" :width "260px"}]
     [custom-ui/input-field :datetime data :label "Next Appointment" :type "datetime-local" :shrink true
      :style {:margin-left "12px" :margin-right "12px" :width "260px"}]])) 

(defn pickers []
  (let [state (r/atom {})]
    (fn []
      [:div
       [ui/typography {:variant "display2"
                       :color "default"}
        "Pickers examples"]
       [:br] [:br]
       [:div.example
        [common/code-button state :example1]
        [ui/collapse {:in (:example1 @state)
                      :timeout "auto"
                      :unmount-on-exit true}
         [:pre
          "
(defn pickers-example []
  (let [data (r/atom {:time \"15:34\"
                      :datetime \"2018-05-24T10:30\"})]
    [:div {:style {:display \"flex\" :flex-wrap \"wrap\"}}
     [custom-ui/input-field :date data :label \"Birthday\" :type \"date\" :shrink true
      :style {:margin-left \"12px\" :margin-right \"12px\" :width \"260px\"}]
     [custom-ui/input-field :time data :label \"Alarm\" :type \"time\" :shrink true
      :style {:margin-left \"12px\" :margin-right \"12px\" :width \"260px\"}]
     [custom-ui/input-field :datetime data :label \"Next Appointment\" :type \"datetime-local\" :shrink true
      :style {:margin-left \"12px\" :margin-right \"12px\" :width \"260px\"}]]))"
          ]]
        [pickers-example]]])))
