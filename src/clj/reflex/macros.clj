(ns reflex.macros)

(defmacro computed-observable [& body]
  `(let [co# (reflex.core/ComputedObservable. nil true (fn [] ~@body) (gensym "computed-observable") {} {})]
     @co# ;;initial deref to setup watchers
     co#))

(defmacro constrain!
  "Reruns body whenever atoms deferenced in the body change. Body should side effect."
  [& body]
  `(let [co# (computed-observable ~@body)]
     (add-watch co# :reflex-constraint (fn [] ~@body))
     co#))
