(ns reagent-material-ui-1.examples.text-field
  (:require [reagent.core :as r]
            [re-material-ui-1.core :as ui]
            [re-material-ui-1.custom :as custom-ui]
            [reagent-material-ui-1.examples.common :as common]))


(defn text-field-example1 []
  (let [input (r/atom {:error "wrong input"})
        style {:margin-left "12px"
               :margin-right "12px"
               :width "200px"}
        options [{:id "dollars" :shortcut "$"}
                 {:id "euros" :shortcut "€"}
                 {:id "BTC" :shortcut "฿"}
                 {:id "JPY" :shortcut "¥"}]]
    (fn []
      [:div {:style {:display "flex" :flex-wrap "wrap"}}
       [ui/text-field {:id :name :label "Name" :style style 
                       :value (:name @input)
                       :on-change #(swap! input assoc :name (.. % -target -value))
                       :margin "normal"}]
       [custom-ui/input-field :username input :label "Username" :style style :required true
        :start-adornment [ui/account-circle-icon]]
       [custom-ui/input-field :password input :label "Password" :class-name "field text-field"
        :type (if (:password-visible? @input) "text" "password") :required true
        :end-adornment [ui/icon-button {:aria-label "toggle password"
                                        :on-click #(swap! input assoc :password-visible?
                                                          (not (:password-visible? @input)))}
                          (if (:password-visible? @input)
                            [ui/visibility-off-icon]
                            [ui/visibility-icon])]]
       [custom-ui/input-field :error input :label "Error" :style style :error-text "Something wrong"
        :default-value "Wrong input"]
       [custom-ui/input-field :search input :label "Search" :style style :type "search"]
       [custom-ui/input-field :age input :label "Age" :style style :type "number" :end-adornment "years"]
       [custom-ui/select :select input options :style style :label "Currency"
        :keys {:value :id :label :shortcut}]
       [custom-ui/select :select-native input options :style style :label "Native Select"
        :keys {:value :id :label :shortcut} :native-select? true]
       [custom-ui/input-field :disabled input :label "Disabled" :style style :disabled true]
       [custom-ui/input-field :multiline input :label "Multiline" :class-name "multi-text-field" :multiline true]])))


(defn text-field []
  (let [state (r/atom {})]
    (fn []
      [:div
       [ui/typography {:variant "display2"
                       :color "default"}
        "Text field examples"]
       [:br] [:br]
       [:div.example
        [common/code-button state :example1]
        [ui/collapse {:in (:example1 @state)
                      :timeout "auto"
                      :unmount-on-exit true}
         [:pre
          "
(defn text-field-example1 []
  (let [input (r/atom {:error \"wrong input\"})
        style {:margin-left \"12px\"
               :margin-right \"12px\"
               :width \"200px\"}
        options [{:id \"dollars\" :shortcut \"$\"}
                 {:id \"euros\" :shortcut \"€\"}
                 {:id \"BTC\" :shortcut \"฿\"}
                 {:id \"JPY\" :shortcut \"¥\"}]]
    (fn []
      [:div {:style {:display \"flex\" :flex-wrap \"wrap\"}}
       [ui/text-field {:id :name :label \"Name\" :style style 
                       :value (:name @input)
                       :on-change #(swap! input assoc :name (.. % -target -value))
                       :margin \"normal\"}]
       [custom-ui/input-field :username input :label \"Username\" :style style :required true
        :start-adornment [ui/account-circle-icon]]
       [custom-ui/input-field :password input :label \"Password\" :class-name \"field text-field\"
        :type (if (:password-visible? @input) \"text\" \"password\") :required true
        :end-adornment [ui/icon-button {:aria-label \"toggle password\"
                                        :on-click #(swap! input assoc :password-visible?
                                                          (not (:password-visible? @input)))}
                          (if (:password-visible? @input)
                            [ui/visibility-off-icon]
                            [ui/visibility-icon])]]
       [custom-ui/input-field :error input :label \"Error\" :style style :error-text \"Something wrong\"
        :default-value \"Wrong input\"]
       [custom-ui/input-field :search input :label \"Search\" :style style :type \"search\"]
       [custom-ui/input-field :age input :label \"Age\" :style style :type \"number\" :end-adornment \"years\"]
       [custom-ui/select :select input options :style style :label \"Currency\"
        :keys {:value :id :label :shortcut}]
       [custom-ui/select :select-native input options :style style :label \"Native Select\"
        :keys {:value :id :label :shortcut} :native-select? true]
       [custom-ui/input-field :disabled input :label \"Disabled\" :style style :disabled true]
       [custom-ui/input-field :multiline input :label \"Multiline\" :class-name \"multi-text-field\" :multiline true]])))"
          ]]
        [text-field-example1]]])))

