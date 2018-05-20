(ns ^:figwheel-no-load reagent-material-ui-1.dev
  (:require
    [reagent-material-ui-1.core :as core]
    [devtools.core :as devtools]))

(devtools/install!)

(enable-console-print!)

(core/init!)
