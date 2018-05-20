(ns reagent-material-ui-1.prod
  (:require [reagent-material-ui-1.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
