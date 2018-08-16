(ns ^:figwheel-no-load scramble-frontend.dev
  (:require
    [scramble-frontend.core :as core]
    [devtools.core :as devtools]))


(enable-console-print!)

(devtools/install!)

(core/init!)
