# 0-1 Knapsack Solution in Clojure

This is my solution to the [0-1 Knapsack Problem](https://github.com/micahalles/doll-smuggler).

## Installation
To install this project from the command line, run
```
- git clone https://github.com/toreyhickman/0-1-Knapsack-Problem-in-Clojure
- cd knapsack
- lein uberjar
```

## Usage

From the command line run
```
$ java -jar target/knapsack-0.1.0-standalone.jar <yourfile.txt>
```

## Testing
To run the test suite, from the command line run
```
lein test
```


## Examples
Passing as an argument a file (test.txt) containing
```
max weight: 5

available dolls:

name    weight value
one         2     3
two         3     4
three       4     5
four        5     6
```

displays
```
packed dolls:

name    weight    value
two         3        4
one         2        3
```


## License

Distributed under the Eclipse Public License, the same as Clojure.
