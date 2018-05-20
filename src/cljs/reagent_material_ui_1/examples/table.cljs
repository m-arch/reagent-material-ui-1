(ns reagent-material-ui-1.examples.table
  (:require [reagent.core :as r]
            [re-material-ui-1.core :as ui]
            [re-material-ui-1.custom :as custom-ui]
            [reagent-material-ui-1.examples.common :as common]))

(defn table-example [] 
  (let [state (r/atom {:order "asc"
                       :page 0
                       :rows-per-page 5
                       :selected []})
        data [{:name "Cupcake" :calories 305 :fat 3.7 :carbs 67 :proteins 4.3}
              {:name "Donut" :calories 452 :fat 25.0 :carbs 51 :proteins 4.9}
              {:name "Eclair" :calories 262 :fat 16.0 :carbs 24 :proteins 6.0}
              {:name "Frozen yoghurt" :calories 159 :fat 6.0 :carbs 24 :proteins 4.0}
              {:name "Gingerbread" :calories 356 :fat 16.0 :carbs 49 :proteins 3.9}
              {:name "Honeycomb" :calories 408 :fat 3.2 :carbs 87 :proteins 6.5}
              {:name "Ice cream sandwich" :calories 237 :fat 9.0 :carbs 37 :proteins 4.3}
              {:name "Jelly Bean" :calories 375 :fat 0.0 :carbs 94 :proteins 0.0}
              {:name "KitKat" :calories 518 :fat 26.0 :carbs 65 :proteins 7.0}
              {:name "Lollipop" :calories 392 :fat 0.2 :carbs 98 :proteins 0.0}
              {:name "Marshmallow" :calories 318 :fat 0 :carbs 81 :proteins 2.0}
              {:name "Nougat" :calories 360 :fat 19.0 :carbs 9 :proteins 37.0}
              {:name "Oreo" :calories 437 :fat 18.0 :carbs 63 :proteins 4.0}]
        column-data [{:key :name :numeric false :disable-padding true :label "Dessert (100g serving)"
                      :component "th"}
                     {:key :calories :numeric true :disable-padding false :label "Calories"}
                     {:key :fat :numeric true :disable-padding false :label "Fat"}
                     {:key :carbs :numeric true :disable-padding false :label "Carbs"}
                     {:key :proteins :numeric true :disable-padding false :label "Proteins"}]]
    (fn []
      [ui/paper {:style {:width "100%"
                         :overflow-x "auto"}}
       [ui/toolbar {:style (if (> (count (:selected @state)) 0)
                             {:padding-right "22px"
                              :color ui/red800
                              :background-color ui/red200}
                             {})}
        [:div {:style {:flex "0 0 auto"}}
         (if (> (count (:selected @state)) 0)
           [ui/typography {:color "inherit"
                           :variant "subheading"}
            (str (count (:selected @state)) " selected")]
           [ui/typography {:variant "title"
                           :style {:margin-top "12px"
                                   :margin-left "22px"}}
            "Nutrition"])]
        [:div {:style {:flex "1 1 100%"}}]
        [:div
         (when (> (count (:selected @state)) 0)
           [ui/tooltip {:title "delete"}
            [ui/icon-button
             [ui/delete-icon]]])]]
       [:div {:style {:overflow-x "auto"}}
        [ui/table {:style {:min-width "700px"}}
         [ui/table-head
          [ui/table-row
           [ui/table-cell {:padding "checkbox"}
            [ui/checkbox {:indeterminate (and (> (count (:selected @state)) 0)
                                              (< (count (:selected @state)) (count data)))
                          :checked (= (count (:selected @state)) (count data))
                          :on-change #(if %2
                                        (swap! state assoc
                                               :selected (map (fn [row]
                                                                {:name (:name row)}) data))
                                        (swap! state assoc :selected []))}]]
           (doall
            (for [head column-data]
              [ui/table-cell {:key (:key head)
                              :numeric (:numeric head)
                              :style (if (:disable-padding head)
                                       {:padding "0"}
                                       {})}
               (when (not (:numeric head))
                 (:label head))
               [ui/tooltip {:title "sort"
                            :enter-delay 300}
                [ui/table-sort-label {:active (= (:order-by @state) (:key head))
                                      :direction (:order @state)
                                      :on-click #(swap! state assoc
                                                        :order-by (:key head)
                                                        :order (if (= (:order @state) "asc")
                                                                 "desc"
                                                                 "asc"))}]]
               (when (:numeric head)
                 (:label head))]))]]
         [ui/table-body
          (doall
           (for [item (if (:order-by @state)
                        (take (:rows-per-page @state) (drop (* (:page @state) (:rows-per-page @state))
                                                            (sort-by (:order-by @state)
                                                                     (if (= (:order @state) "asc")
                                                                       < >) data)))
                        (take (:rows-per-page @state) (drop (* (:page @state)  (:rows-per-page @state))
                                                            data)))]
             [ui/table-row {:key (:name item)}
              [ui/table-cell {:padding "checkbox"}
               [ui/checkbox {:checked (= 1 (count (filter #(= (:name %) (:name item)) (:selected @state))))
                             :on-change #(if %2
                                           (swap! state assoc
                                                  :selected (conj (:selected @state)
                                                                  {:name (:name item)}))
                                           (swap! state assoc 
                                                  :selected (filter (fn [row]
                                                                      (not= (:name row) (:name item)))
                                                                    (:selected @state))))}]]
              (doall
               (for [[k v] (seq item)
                     :let [head (first (filter #(= (:key %) k) column-data))]]
                 [ui/table-cell {:key k
                                 :style (if (:disable-padding head)
                                          {:padding "0"}
                                          {})
                                 :numeric (:numeric head)
                                 :component (:component head)}
                  v]))]))]]]
       [ui/table-pagination {:component "div"
                             :count (count data)
                             :rows-per-page (:rows-per-page @state)
                             :page (:page @state)
                             :back-icon-button-props {:aria-label "Pervious page"}
                             :next-icon-button-props {:aria-label "Next page"}
                             :on-change-page #(swap! state assoc :page %2)
                             :on-change-rows-per-page #(swap! state assoc
                                                              :rows-per-page (.. % -target -value))}]])))

(defn table-example2 []
  (let [data [{:name "Cupcake" :calories 305 :fat 3.7 :carbs 67 :proteins 4.3}
              {:name "Donut" :calories 452 :fat 25.0 :carbs 51 :proteins 4.9}
              {:name "Eclair" :calories 262 :fat 16.0 :carbs 24 :proteins 6.0}
              {:name "Frozen yoghurt" :calories 159 :fat 6.0 :carbs 24 :proteins 4.0}
              {:name "Gingerbread" :calories 356 :fat 16.0 :carbs 49 :proteins 3.9}
              {:name "Honeycomb" :calories 408 :fat 3.2 :carbs 87 :proteins 6.5}
              {:name "Ice cream sandwich" :calories 237 :fat 9.0 :carbs 37 :proteins 4.3}]]
    [custom-ui/datatable [{:key :name :numeric false :label "Dessert (100g serving)"
                           :component "th"}
                          {:key :calories :numeric true :label "Calories"}
                          {:key :fat :numeric true :label "Fat"}
                          {:key :carbs :numeric true :label "Carbs"}
                          {:key :proteins :numeric true :label "Proteins"}]
     data :no-paging? true :identifier :name :title "Nutrition"]))

(defn table-example3 []
  (let [data [{:name "Cupcake" :calories 305 :fat 3.7 :carbs 67 :proteins 4.3}
              {:name "Donut" :calories 452 :fat 25.0 :carbs 51 :proteins 4.9}
              {:name "Eclair" :calories 262 :fat 16.0 :carbs 24 :proteins 6.0}
              {:name "Frozen yoghurt" :calories 159 :fat 6.0 :carbs 24 :proteins 4.0}
              {:name "Gingerbread" :calories 356 :fat 16.0 :carbs 49 :proteins 3.9}
              {:name "Honeycomb" :calories 408 :fat 3.2 :carbs 87 :proteins 6.5}
              {:name "Ice cream sandwich" :calories 237 :fat 9.0 :carbs 37 :proteins 4.3}]
        state (r/atom {})]
    [custom-ui/datatable [{:key :radio :eq (fn [row]
                                             [ui/radio {:checked (= (:radio @state) (:name row))
                                                        :on-change #(swap! state assoc :radio (:name row))
                                                        :value (:name row)}])}
                          {:key :name :numeric false :disable-padding true :label "Dessert (100g serving)"
                           :component "th"}
                          {:key :calories :numeric true :sortable? true :disable-padding true :label "Calories"}
                          {:key :fat :numeric true :label "Fat"}
                          {:key :carbs :numeric true :label "Carbs"}
                          {:key :proteins :numeric true :label "Proteins"}]
     data :identifier :name :title "Nutrition"]))

(defn table-example4 []
  (let [data [{:name "Cupcake" :calories 305 :fat 3.7 :carbs 67 :proteins 4.3}
              {:name "Donut" :calories 452 :fat 25.0 :carbs 51 :proteins 4.9}
              {:name "Eclair" :calories 262 :fat 16.0 :carbs 24 :proteins 6.0}
              {:name "Frozen yoghurt" :calories 159 :fat 6.0 :carbs 24 :proteins 4.0}
              {:name "Gingerbread" :calories 356 :fat 16.0 :carbs 49 :proteins 3.9}
              {:name "Honeycomb" :calories 408 :fat 3.2 :carbs 87 :proteins 6.5}
              {:name "Ice cream sandwich" :calories 237 :fat 9.0 :carbs 37 :proteins 4.3}]
        head-cell-style {:font-size "14px" :color "white" :background-color "black"}]
    [custom-ui/datatable [{:key :name :numeric false :label "Dessert (100g serving)"
                           :component "th"}
                          {:key :calories :numeric true :label "Calories"}
                          {:key :fat :numeric true :label "Fat"}
                          {:key :carbs :numeric true :label "Carbs"}
                          {:key :proteins :numeric true :label "Proteins"}]
     data :identifier :name :rows-per-page 10 :head-style head-cell-style]))

(defn table []
  (let [state (r/atom {})]
    (fn []
      [:div
       [ui/typography {:variant "display2"
                       :color "default"}
        "Table examples"]
       [:br] [:br]
       [:div.example
        [common/code-button state :example1]
        [ui/collapse {:in (:example1 @state)
                      :timeout "auto"
                      :unmount-on-exit true}
         [:pre
          "
(defn table-example []
  (let [state (r/atom {:order \"asc\"
                       :page 0
                       :rows-per-page 5
                       :selected []})
        data [{:name \"Cupcake\" :calories 305 :fat 3.7 :carbs 67 :proteins 4.3}
              {:name \"Donut\" :calories 452 :fat 25.0 :carbs 51 :proteins 4.9}
              {:name \"Eclair\" :calories 262 :fat 16.0 :carbs 24 :proteins 6.0}
              {:name \"Frozen yoghurt\" :calories 159 :fat 6.0 :carbs 24 :proteins 4.0}
              {:name \"Gingerbread\" :calories 356 :fat 16.0 :carbs 49 :proteins 3.9}
              {:name \"Honeycomb\" :calories 408 :fat 3.2 :carbs 87 :proteins 6.5}
              {:name \"Ice cream sandwich\" :calories 237 :fat 9.0 :carbs 37 :proteins 4.3}
              {:name \"Jelly Bean\" :calories 375 :fat 0.0 :carbs 94 :proteins 0.0}
              {:name \"KitKat\" :calories 518 :fat 26.0 :carbs 65 :proteins 7.0}
              {:name \"Lollipop\" :calories 392 :fat 0.2 :carbs 98 :proteins 0.0}
              {:name \"Marshmallow\" :calories 318 :fat 0 :carbs 81 :proteins 2.0}
              {:name \"Nougat\" :calories 360 :fat 19.0 :carbs 9 :proteins 37.0}
              {:name \"Oreo\" :calories 437 :fat 18.0 :carbs 63 :proteins 4.0}]
        column-data [{:key :name :numeric false :disable-padding true :label \"Dessert (100g serving)\"
                      :component \"th\"}
                     {:key :calories :numeric true :disable-padding false :label \"Calories\"}
                     {:key :fat :numeric true :disable-padding false :label \"Fat\"}
                     {:key :carbs :numeric true :disable-padding false :label \"Carbs\"}
                     {:key :proteins :numeric true :disable-padding false :label \"Proteins\"}]]
    (fn []
      [ui/paper {:style {:width \"100%\"
                         :overflow-x \"auto\"}}
       [ui/toolbar {:style (if (> (count (:selected @state)) 0)
                             {:padding-right \"22px\"
                              :color ui/red800
                              :background-color ui/red200}
                             {})}
        [:div {:style {:flex \"0 0 auto\"}}
         (if (> (count (:selected @state)) 0)
           [ui/typography {:color \"inherit\"
                           :variant \"subheading\"}
            (str (count (:selected @state)) \" selected\")]
           [ui/typography {:variant \"title\"
                           :style {:margin-top \"12px\"
                                   :margin-left \"22px\"}}
            \"Nutrition\"])]
        [:div {:style {:flex \"1 1 100%\"}}]
        [:div
         (when (> (count (:selected @state)) 0)
           [ui/tooltip {:title \"delete\"}
            [ui/icon-button
             [ui/delete-icon]]])]]
       [:div {:style {:overflow-x \"auto\"}}
        [ui/table {:style {:min-width \"700px\"}}
         [ui/table-head
          [ui/table-row
           [ui/table-cell {:padding \"checkbox\"}
            [ui/checkbox {:indeterminate (and (> (count (:selected @state)) 0)
                                              (< (count (:selected @state)) (count data)))
                          :checked (= (count (:selected @state)) (count data))
                          :on-change #(if %2
                                        (swap! state assoc
                                               :selected (map (fn [row]
                                                                {:name (:name row)}) data))
                                        (swap! state assoc :selected []))}]]
           (doall
            (for [head column-data]
              [ui/table-cell {:key (:key head)
                              :numeric (:numeric head)
                              :style (if (:disable-padding head)
                                       {:padding \"0\"}
                                       {})}
               (when (not (:numeric head))
                 (:label head))
               [ui/tooltip {:title \"sort\"
                            :enter-delay 300}
                [ui/table-sort-label {:active (= (:order-by @state) (:key head))
                                      :direction (:order @state)
                                      :on-click #(swap! state assoc
                                                        :order-by (:key head)
                                                        :order (if (= (:order @state) \"asc\")
                                                                 \"desc\"
                                                                 \"asc\"))}]]
               (when (:numeric head)
                 (:label head))]))]]
         [ui/table-body
          (doall
           (for [item (if (:order-by @state)
                        (take (:rows-per-page @state) (drop (* (:page @state) (:rows-per-page @state))
                                                            (sort-by (:order-by @state)
                                                                     (if (= (:order @state) \"asc\")
                                                                       < >) data)))
                        (take (:rows-per-page @state) (drop (* (:page @state)  (:rows-per-page @state))
                                                            data)))]
             [ui/table-row {:key (:name item)}
              [ui/table-cell {:padding \"checkbox\"}
               [ui/checkbox {:checked (= 1 (count (filter #(= (:name %) (:name item)) (:selected @state))))
                             :on-change #(if %2
                                           (swap! state assoc
                                                  :selected (conj (:selected @state)
                                                                  {:name (:name item)}))
                                           (swap! state assoc 
                                                  :selected (filter (fn [row]
                                                                      (not= (:name row) (:name item)))
                                                                    (:selected @state))))}]]
              (doall
               (for [[k v] (seq item)
                     :let [head (first (filter #(= (:key %) k) column-data))]]
                 [ui/table-cell {:key k
                                 :style (if (:disable-padding head)
                                          {:padding \"0\"}
                                          {})
                                 :numeric (:numeric head)
                                 :component (:component head)}
                  v]))]))]]]
       [ui/table-pagination {:component \"div\"
                             :count (count data)
                             :rows-per-page (:rows-per-page @state)
                             :page (:page @state)
                             :back-icon-button-props {:aria-label \"Pervious page\"}
                             :next-icon-button-props {:aria-label \"Next page\"}
                             :on-change-page #(swap! state assoc :page %2)
                             :on-change-rows-per-page #(swap! state assoc
                                                              :rows-per-page (.. % -target -value))}]])))"
          ]]
        [:div
         [table-example]
         ]]
       [:br] [:br]
       [ui/typography {:variant "display1"
                       :color "default"}
        "Custom table examples"]
       [:br] [:br]
       [:div.example
        [common/code-button state :example2]
        [ui/collapse {:in (:example2 @state)
                      :timeout "auto"
                      :unmount-on-exit true}
         [:pre
          "
(defn table-example2 []
  (let [data [{:name \"Cupcake\" :calories 305 :fat 3.7 :carbs 67 :proteins 4.3}
              {:name \"Donut\" :calories 452 :fat 25.0 :carbs 51 :proteins 4.9}
              {:name \"Eclair\" :calories 262 :fat 16.0 :carbs 24 :proteins 6.0}
              {:name \"Frozen yoghurt\" :calories 159 :fat 6.0 :carbs 24 :proteins 4.0}
              {:name \"Gingerbread\" :calories 356 :fat 16.0 :carbs 49 :proteins 3.9}
              {:name \"Honeycomb\" :calories 408 :fat 3.2 :carbs 87 :proteins 6.5}
              {:name \"Ice cream sandwich\" :calories 237 :fat 9.0 :carbs 37 :proteins 4.3}]]
    [custom-ui/datatable [{:key :name :numeric false :label \"Dessert (100g serving)\"
                           :component \"th\"}
                          {:key :calories :numeric true :label \"Calories\"}
                          {:key :fat :numeric true :label \"Fat\"}
                          {:key :carbs :numeric true :label \"Carbs\"}
                          {:key :proteins :numeric true :label \"Proteins\"}]
     data :no-paging? true :identifier :name :title \"Nutrition\"]))"
          ]]
        [:div
         [table-example2]]]
       [:br] [:br]
       [:div.example
        [common/code-button state :example3]
        [ui/collapse {:in (:example3 @state)
                      :timeout "auto"
                      :unmount-on-exit true}
         [:pre
          "
(defn table-example3 []
  (let [data [{:name \"Cupcake\" :calories 305 :fat 3.7 :carbs 67 :proteins 4.3}
              {:name \"Donut\" :calories 452 :fat 25.0 :carbs 51 :proteins 4.9}
              {:name \"Eclair\" :calories 262 :fat 16.0 :carbs 24 :proteins 6.0}
              {:name \"Frozen yoghurt\" :calories 159 :fat 6.0 :carbs 24 :proteins 4.0}
              {:name \"Gingerbread\" :calories 356 :fat 16.0 :carbs 49 :proteins 3.9}
              {:name \"Honeycomb\" :calories 408 :fat 3.2 :carbs 87 :proteins 6.5}
              {:name \"Ice cream sandwich\" :calories 237 :fat 9.0 :carbs 37 :proteins 4.3}]
        state (r/atom {})]
    [custom-ui/datatable [{:key :radio :eq (fn [row]
                                             [ui/radio {:checked (= (:radio @state) (:name row))
                                                        :on-change #(swap! state assoc :radio (:name row))
                                                        :value (:name row)}])}
                          {:key :name :numeric false :disable-padding true :label \"Dessert (100g serving)\"
                           :component \"th\"}
                          {:key :calories :numeric true :sortable? true :disable-padding true :label \"Calories\"}
                          {:key :fat :numeric true :label \"Fat\"}
                          {:key :carbs :numeric true :label \"Carbs\"}
                          {:key :proteins :numeric true :label \"Proteins\"}]
     data :identifier :name :title \"Nutrition\"]))"
          ]]
        [:div
         [table-example3]]]
       [:br] [:br]
       [:div.example
        [common/code-button state :example4]
        [ui/collapse {:in (:example4 @state)
                      :timeout "auto"
                      :unmount-on-exit true}
         [:pre
          "
(defn table-example4 []
  (let [data [{:name \"Cupcake\" :calories 305 :fat 3.7 :carbs 67 :proteins 4.3}
              {:name \"Donut\" :calories 452 :fat 25.0 :carbs 51 :proteins 4.9}
              {:name \"Eclair\" :calories 262 :fat 16.0 :carbs 24 :proteins 6.0}
              {:name \"Frozen yoghurt\" :calories 159 :fat 6.0 :carbs 24 :proteins 4.0}
              {:name \"Gingerbread\" :calories 356 :fat 16.0 :carbs 49 :proteins 3.9}
              {:name \"Honeycomb\" :calories 408 :fat 3.2 :carbs 87 :proteins 6.5}
              {:name \"Ice cream sandwich\" :calories 237 :fat 9.0 :carbs 37 :proteins 4.3}]
        head-cell-style {:font-size \"14px\" :color \"white\" :background-color \"black\"}]
    [custom-ui/datatable [{:key :name :numeric false :label \"Dessert (100g serving)\"
                           :component \"th\"}
                          {:key :calories :numeric true :label \"Calories\"}
                          {:key :fat :numeric true :label \"Fat\"}
                          {:key :carbs :numeric true :label \"Carbs\"}
                          {:key :proteins :numeric true :label \"Proteins\"}]
     data :identifier :name :rows-per-page 10 :head-style head-cell-style]))"
          ]]
        [:div
         [table-example4]]]])))
