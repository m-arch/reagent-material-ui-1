(ns reagent-material-ui-1.utils
  (:require [cljsjs.material-ui]
            [cljsjs.material-ui-svg-icons]
            [camel-snake-kebab.core :refer [->kebab-case]]
            [reagent.core :as r])
  (:require-macros [reagent-material-ui-1.macros :refer [adapt-components]]))

;;;;;;COMPONENTS;;;;;; 
(adapt-components)
(def list-component
  (r/adapt-react-class (aget js/MaterialUI (name :List))))

;;;;;;;;STYLES;;;;;;;;
(def create-mui-theme-fn
  (aget js/MaterialUIStyles (name :createMuiTheme)))
(def mui-theme-provider
  (r/adapt-react-class (aget js/MaterialUIStyles (name :MuiThemeProvider))))
(def with-styles-fn
  (aget js/MaterialUIStyles (name :withStyles)))
 
