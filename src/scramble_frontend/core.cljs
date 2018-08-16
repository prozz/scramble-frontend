(ns scramble-frontend.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as r]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))

(def url "http://localhost:3000/scramble")

(defonce answer (r/atom "don't know yet"))

(defn input [id label value]
  [:label (str label ": ")
   [:input {:id id
            :type "text"
            :value @value
            :on-change #(reset! value (-> % .-target .-value))}]])

(defn update-answer [s1 s2]
  (go (let [response (<! (http/get url
                                   {:with-credentials? false
                                    :query-params {"s1" @s1 "s2" @s2}}))]
        (if (= 200 (:status response))
          (reset! answer (str (get-in response [:body :scramble])))
          (reset! answer "bad request or some other problem!")))))

(defn scramble []
  (let [s1 (r/atom "")
        s2 (r/atom "")]
    [:div [:h2 "Ask me..."]
     [:div [input "s1" "Alphabet" s1]]
     [:div [input "s2" "String to scramble" s2]]
     [:div [:button {:on-click #(update-answer s1 s2)} "Scramble?"]]
     [:div "Answer is: " @answer]]))

(defn mount-root []
  (r/render [scramble] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
