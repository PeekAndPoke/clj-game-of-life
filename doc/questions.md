# (seq x) over (not (empty? x))

The docs for "empty?" state:

```
clojure.core/empty?  
([coll])  
  Returns true if coll has no items - same as (not (seq coll)).
  Please use the idiom (seq x) rather than (not (empty? x))
```

Q: What does (seq x) do? Why is this more idiomatic than (not (empty? x)) ?


# fits-table?

```
clojure.core/fits-table?
([ints])
  Returns true if the collection of ints can fit within the
  max-table-switch-size, false otherwise.
```

Q: What is this? What is it used for? What is "max-table-switch-size"


# fn? vs ifn?

```
clojure.core/fn?
([x])
  Returns true if x implements Fn, i.e. is an object created via fn.

clojure.core/ifn?
([x])
  Returns true if x implements IFn. Note that many data structures
  (e.g. sets and maps) implement IFn
```

Q: What is the difference between Fn and IFn? 


# integral? vs integer?

```
clojure.pprint/integral?
([x])
  returns true if a number is actually an integer (that is, has no fractional part)

clojure.core/integer?
([n])
  Returns true if n is an integer
```

Q: What does 'is actually an integer' mean?


