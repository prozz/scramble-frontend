(ns scramble-frontend.prod
  (:require
    [scramble-frontend.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
