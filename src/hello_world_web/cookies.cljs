(ns hello-world-web.cookies
  (:import goog.net.Cookies))

(def cookies (Cookies. js/document))

(defn clear-cookies
 "Removes cookies from the document of the current path and domain."
  []
  (.clear cookies))

(defn contains-key?
 "Is there a cookie with the given name?"
  [key]
  (.containsKey cookies (name key)))

(defn contains-value?
 "Is there a cookie with the given value?"
  [value]
  (.containsValue cookies value))

(defn get-value
 "Get the value of the cookie with the given key. Return opt as a default value."
  ([key]
   (.get cookies (name key)))
  ([key opt]
   (.get cookies (name key) opt)))

(defn get-count
  "Returns the number of cookies for this document."
  []
  (.getCount cookies))

(defn get-keys
  "Returns the keys."
  []
  (.getKeys cookies))

(defn get-values
  "Returns the values."
  []
  (.getValues cookies))

(defn is-empty?
  "Is the cookies object empty?"
  []
  (.isEmpty cookies))

(defn enabled?
  "Are cookies enabled?"
  []
  (.isEnabled cookies))

(defn remove-cookie
  "Removes the cookie. Opts are :path, :domain."
  [key & opts]
  (let [{:keys [path domain]} (apply hash-map opts)]
    (.remove cookies (name key) path domain)))

(defn set-cookie
  [key value & opts]
  "Sets the provided cookie. Opts are :max-age, :path, :domain, :secure.
  Default age is -1, a session cookie."
  (when-let [key (and (.isValidName cookies (name key)) (name key))]
   (when (.isValidValue cookies value)
    (let [{:keys [max-age path domain secure?]} (apply hash-map opts)]
      (.set cookies key value (if (boolean max-age) max-age -1)) path domain secure?))))
