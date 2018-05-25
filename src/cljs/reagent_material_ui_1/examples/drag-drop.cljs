(ns reagent-material-ui-1.examples.drag-drop
  (:require [reagent.core :as r]
            [re-material-ui-1.core :as ui]
            [re-material-ui-1.custom :as custom-ui]
            [reagent-material-ui-1.examples.common :as common]
            [goog.events :as events]
            [goog.style :as style]
            [goog.dom :as dom])
  (:import [goog.fx Dragger]
           [goog.fx DragListGroup]
           [goog.events EventType]
           [goog.fx DragListDirection]))

(defn get-item-new-position [evt cell-height cols id]
  (let [main-board (.getBoundingClientRect (.getElementById js/document id))
        cell-width (/ (- (.-right main-board) (.-left main-board)) cols)
        x-position (js/parseInt (/ (- (.-clientX evt) (.-left main-board))
                                   cell-width))
        y-position (js/parseInt (/ (- (.-clientY evt) (.-top main-board))
                                   (+ 10 cell-height)))]
    (+  (* cols y-position) x-position)))

(defn insert-at [position elements value]
  (let [[before after] (split-at position elements)]
    (concat before (vector value) after)))


(defn drag-drop-example []
  (let [parent-style {:display "flex" :flex-wrap "wrap" :justify-content "space-around"
                      :overflow "hidden" :background-color "white"}
        items (r/atom [{:key "dne" :url "http://25.media.tumblr.com/tumblr_m4okzyBSlg1qd477zo1_1280.jpg"}
                       {:key "5n8" :url "http://24.media.tumblr.com/tumblr_lovipjvvJ71qjpedeo1_500.jpg"}
                       {:key "55e" :url "http://24.media.tumblr.com/tumblr_lwl7eyzlpm1qmjao5o1_500.jpg"}
                       {:key "7uc" :url "http://25.media.tumblr.com/tumblr_lhb7khj1Pk1qcn249o1_400.gif"}
                       {:key "bn9" :url "http://24.media.tumblr.com/tumblr_m4l74aiZsz1qd477zo1_500.jpg"}
                       {:key "a1l" :url "http://24.media.tumblr.com/tumblr_m4gbm0zscc1qz6jrko1_1280.jpg"}
                       {:key "864" :url "http://24.media.tumblr.com/tumblr_lpogbck7Fy1qdrnl5o1_500.gif"}])
        dragged-item (r/atom {})
        cols 3
        cell-height 160]
    (fn []
      [:div#cats {:style parent-style}
       [ui/grid-list {:cell-height cell-height :cols cols 
                      :on-drag-over #(do
                                       (.preventDefault %)
                                       (let [new-pos (get-item-new-position % cell-height cols "cats")]
                                         (reset! items (insert-at new-pos (filter (fn [elem]
                                                                                    (not= (:key elem)
                                                                                          (:key @dragged-item)))
                                                                                  @items) (assoc @dragged-item :hidden true)))))}
        (doall
         (for [item @items]
           [ui/grid-list-tile {:key (:key item)
                               :class-name (when (:hidden item)
                                             "blue-box")
                               :on-drag-end #(reset! items (map (fn [elem]
                                                                  (dissoc elem :hidden)) @items))
                               :on-drag-start #(do
                                                 (reset! items (doall
                                                                (for [elem @items]
                                                                  (if (= (:key elem) (:key item))
                                                                    (assoc item :hidden true)
                                                                    elem))))
                                                 (reset! dragged-item item))}
            [:img {:id (:key item) :src (:url item)}]]))]])))




(defn drag-drop-example2 []
  (let [boards (r/atom [{:title "Board 1"
                         :key "board1"
                         :content [{:key "1" :text "Todo text 1"}
                                   {:key "2" :text "Todo text 2"}
                                   {:key "3" :text "Todo long long long long text 3"}]}
                        {:title "Board 2"
                         :key "board2"
                         :content [{:key "4" :text "Todo text 4"}
                                   {:key "5" :text "Todo long long long long text 5"}]}])
        dragged-item (r/atom {})
        drag-fn (fn [{:keys [board position]}]
                  (doall
                   (for [l-board @boards]
                     (cond (= (:key l-board) (:key board))
                           (assoc l-board :content
                                  (insert-at position
                                             (filter (fn [elem]
                                                       (not= (:key elem) (:key @dragged-item)))
                                                     (:content l-board)) (assoc @dragged-item :block true)))
                           (= (:original-board @dragged-item) (:key l-board))
                           (do
                             (swap! dragged-item assoc :original-board (:key board))
                             (assoc l-board :content (doall
                                                      (for [l-item (:content l-board)]
                                                        (if (:block l-item)
                                                          (-> l-item
                                                              (dissoc :block)
                                                              (assoc :hidden true))
                                                          l-item)))))
                           :true
                           l-board))))
        drag-end-fn (fn []
                      (into []
                            (doall
                             (for [l-board @boards]
                               (assoc l-board :content
                                      (into []
                                            (remove nil?
                                                    (doall
                                                     (for [l-item (:content l-board)]
                                                       (do
                                                         (when (not (:hidden l-item))
                                                           (dissoc l-item :block))))))))))))]
    
    (fn []
      [:div {:style {:display "flex" :overflow-x "auto"}}
       (loop [boards-itm @boards
              board-ctr 0
              board-out ()]
         (if (empty? boards-itm)
           (reverse board-out)
           (recur (rest boards-itm)
                  (inc board-ctr)
                  (let [board (first boards-itm)]
                    (conj board-out 
                          [:div {:id (:key board)
                                 :key (:key board)
                                 :style {:display "flex" :flex-direction "column" :width "200px"
                                         :padding-left "20px"
                                         :padding-top "12px"}
                                 :on-drag-over #(do
                                                  (.preventDefault %)
                                                  (when (empty? (:content board))
                                                    (reset! boards (drag-fn {:board board :position 0}))))} 
                           [ui/app-bar {:position "static" :color "primary"}
                            [ui/toolbar
                             [ui/typography {:variant "title" :color "inherit"}
                              (:title board)]]]
                           (loop [board-items (:content board)
                                  itm-ctr 0
                                  itm-out ()]
                             (if (empty? board-items)
                               (reverse itm-out)
                               (recur (rest board-items)
                                      (inc itm-ctr)
                                      (let [item (first board-items)]
                                        (conj itm-out
                                              [ui/card {:id (:key item ):key (:key item)
                                                        :class-name (if (:hidden item)
                                                                      "hidden-box"
                                                                      (when (:block item)
                                                                        "hover-box"))
                                                        :draggable true
                                                        :on-drag-start #(do
                                                                          (reset! dragged-item
                                                                                  (assoc item :original-board
                                                                                         (:key board)))
                                                                          (reset! boards
                                                                                  (assoc-in @boards
                                                                                            [board-ctr
                                                                                             :content
                                                                                             itm-ctr 
                                                                                             :hidden] true)))
                                                        :on-drag-end #(reset! boards (drag-end-fn))
                                                        :on-drag-over #(do
                                                                         (.preventDefault %)
                                                                         (reset! boards (drag-fn {:board board :position itm-ctr})))} 
                                               [ui/card-content
                                                [ui/typography {:style {}
                                                                :color "textSecondary"}
                                                 (:text item)]]])))))])))))])))


(defn drag-drop []
  (let [state (r/atom {})]
    (fn []
      [:div
       [ui/typography {:variant "display2"
                       :color "default"}
        "Drag-drop examples"]
       [:br] [:br]
       [:div.example
        [common/code-button state :example1]
        [ui/collapse {:in (:example1 @state)
                      :timeout "auto"
                      :unmount-on-exit true}
         [:pre
          "
(defn get-item-new-position [evt cell-height cols id]
  (let [main-board (.getBoundingClientRect (.getElementById js/document id))
        cell-width (/ (- (.-right main-board) (.-left main-board)) cols)
        x-position (js/parseInt (/ (- (.-clientX evt) (.-left main-board))
                                   cell-width))
        y-position (js/parseInt (/ (- (.-clientY evt) (.-top main-board))
                                   (+ 10 cell-height)))]
    (+  (* cols y-position) x-position)))

(defn insert-at [position elements value]
  (let [[before after] (split-at position elements)]
    (concat before (vector value) after)))


(defn drag-drop-example []
  (let [parent-style {:display \"flex\" :flex-wrap \"wrap\" :justify-content \"space-around\"
                      :overflow \"hidden\" :background-color \"white\"}
        items (r/atom [{:key \"dne\" :url \"http://25.media.tumblr.com/tumblr_m4okzyBSlg1qd477zo1_1280.jpg\"}
                       {:key \"5n8\" :url \"http://24.media.tumblr.com/tumblr_lovipjvvJ71qjpedeo1_500.jpg\"}
                       {:key \"55e\" :url \"http://24.media.tumblr.com/tumblr_lwl7eyzlpm1qmjao5o1_500.jpg\"}
                       {:key \"7uc\" :url \"http://25.media.tumblr.com/tumblr_lhb7khj1Pk1qcn249o1_400.gif\"}
                       {:key \"bn9\" :url \"http://24.media.tumblr.com/tumblr_m4l74aiZsz1qd477zo1_500.jpg\"}
                       {:key \"a1l\" :url \"http://24.media.tumblr.com/tumblr_m4gbm0zscc1qz6jrko1_1280.jpg\"}
                       {:key \"864\" :url \"http://24.media.tumblr.com/tumblr_lpogbck7Fy1qdrnl5o1_500.gif\"}])
        dragged-item (r/atom {})
        cols 3
        cell-height 160]
    (fn []
      [:div#cats {:style parent-style}
       [ui/grid-list {:cell-height cell-height :cols cols 
                      :on-drag-over #(do
                                       (.preventDefault %)
                                       (let [new-pos (get-item-new-position % cell-height cols \"cats\")]
                                         (reset! items (insert-at new-pos (filter (fn [elem]
                                                                                    (not= (:key elem)
                                                                                          (:key @dragged-item)))
                                                                                  @items) (assoc @dragged-item :hidden true)))))}
        (doall
         (for [item @items]
           [ui/grid-list-tile {:key (:key item)
                               :class-name (when (:hidden item)
                                             \"blue-box\")
                               :on-drag-end #(reset! items (map (fn [elem]
                                                                  (dissoc elem :hidden)) @items))
                               :on-drag-start #(do
                                                 (reset! items (doall
                                                                (for [elem @items]
                                                                  (if (= (:key elem) (:key item))
                                                                    (assoc item :hidden true)
                                                                    elem))))
                                                 (reset! dragged-item item))}
            [:img {:id (:key item) :src (:url item)}]]))]])))"
          ]]
        [drag-drop-example]]
       [:br] [:br]
       [:div.example
        [common/code-button state :example2]
        [ui/collapse {:in (:example2 @state)
                      :timeout "auto"
                      :unmount-on-exit true}
         [:pre
          "
(defn drag-drop-example2 []
  (let [boards (r/atom [{:title \"Board 1\"
                         :key \"board1\"
                         :content [{:key \"1\" :text \"Todo text 1\"}
                                   {:key \"2\" :text \"Todo text 2\"}
                                   {:key \"3\" :text \"Todo long long long long text 3\"}]}
                        {:title \"Board 2\"
                         :key \"board2\"
                         :content [{:key \"4\" :text \"Todo text 4\"}
                                   {:key \"5\" :text \"Todo long long long long text 5\"}]}])
        dragged-item (r/atom {})
        drag-fn (fn [{:keys [board position]}]
                  (doall
                   (for [l-board @boards]
                     (cond (= (:key l-board) (:key board))
                           (assoc l-board :content
                                  (insert-at position
                                             (filter (fn [elem]
                                                       (not= (:key elem) (:key @dragged-item)))
                                                     (:content l-board)) (assoc @dragged-item :block true)))
                           (= (:original-board @dragged-item) (:key l-board))
                           (do
                             (swap! dragged-item assoc :original-board (:key board))
                             (assoc l-board :content (doall
                                                      (for [l-item (:content l-board)]
                                                        (if (:block l-item)
                                                          (-> l-item
                                                              (dissoc :block)
                                                              (assoc :hidden true))
                                                          l-item)))))
                           :true
                           l-board))))
        drag-end-fn (fn []
                      (into []
                            (doall
                             (for [l-board @boards]
                               (assoc l-board :content
                                      (into []
                                            (remove nil?
                                                    (doall
                                                     (for [l-item (:content l-board)]
                                                       (do
                                                         (when (not (:hidden l-item))
                                                           (dissoc l-item :block))))))))))))]
    
    (fn []
      [:div {:style {:display \"flex\" :overflow-x \"auto\"}}
       (loop [boards-itm @boards
              board-ctr 0
              board-out ()]
         (if (empty? boards-itm)
           (reverse board-out)
           (recur (rest boards-itm)
                  (inc board-ctr)
                  (let [board (first boards-itm)]
                    (conj board-out 
                          [:div {:id (:key board)
                                 :key (:key board)
                                 :style {:display \"flex\" :flex-direction \"column\" :width \"200px\"
                                         :padding-left \"20px\"
                                         :padding-top \"12px\"}
                                 :on-drag-over #(do
                                                  (.preventDefault %)
                                                  (when (empty? (:content board))
                                                    (reset! boards (drag-fn {:board board :position 0})))
                                                  )} 
                           [ui/app-bar {:position \"static\" :color \"primary\"}
                            [ui/toolbar
                             [ui/typography {:variant \"title\" :color \"inherit\"}
                              (:title board)]]]
                           (loop [board-items (:content board)
                                  itm-ctr 0
                                  itm-out ()]
                             (if (empty? board-items)
                               (reverse itm-out)
                               (recur (rest board-items)
                                      (inc itm-ctr)
                                      (let [item (first board-items)]
                                        (conj itm-out
                                              [ui/card {:id (:key item ):key (:key item)
                                                        :class-name (if (:hidden item)
                                                                      \"hidden-box\"
                                                                      (when (:block item)
                                                                        \"hover-box\"))
                                                        :draggable true
                                                        :on-drag-start #(do
                                                                          (reset! dragged-item
                                                                                  (assoc item :original-board
                                                                                         (:key board)))
                                                                          (reset! boards
                                                                                  (assoc-in @boards
                                                                                            [board-ctr
                                                                                             :content
                                                                                             itm-ctr 
                                                                                             :hidden] true)))
                                                        :on-drag-end #(reset! boards (drag-end-fn))
                                                        :on-drag-over #(do
                                                                         (.preventDefault %)
                                                                         (reset! boards (drag-fn {:board board :position itm-ctr})))} 
                                               [ui/card-content
                                                [ui/typography {:style {}
                                                                :color \"textSecondary\"}
                                                 (:text item)]]])))))])))))])))"
          ]]
        [drag-drop-example2]]])))
