(ns cta.questions
  (:require-macro [dommy.macros :as dom])
  (:require [dommy.core :as dommy]
            dommy.template))

(defrecord Question [text response next-question]
  dommy.template/PElement
  (-elem [this] (dommy/node [:div.question 
                             [:span.text text]
                             [:span.response response]
                             [:button.next {:type "button"} "Next"]])))

(def questions 
  (Question. "What's your name" [:input {:type "text"}]
             (Question. "Where does your commute start?" [:input {:type "text"}] 
                        (Question. "And end?" [:input {:type "text"}]))))
