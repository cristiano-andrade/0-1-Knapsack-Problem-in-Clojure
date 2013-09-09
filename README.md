# 0-1 Knapsack Solution in Clojure

This is my solution to the [0-1 Knapsack Problem](https://github.com/micahalles/doll-smuggler).

## Installation
Before installing, ensure that you have installed [Leiningen](https://github.com/technomancy/leiningen).

To install this project from the command line, run
```
$ git clone https://github.com/toreyhickman/0-1-Knapsack-Problem-in-Clojure
$ cd 0-1-Knapsack-Problem-in_clojure/
$ lein uberjar
```

## Usage

From the command line run
```
$ java -jar target/knapsack-0.1.0-standalone.jar <yourfile.txt>
```

## Testing
This project is designed to display output to the console.  It uses the `println` function to do so.  This function returns `nil`.  I did not want to test that my program returns nil, so I added a command to line 157 of /src/knapsack/core.clj to return a vector containing the strings to be printed.  It is currently commented out to:
```
157    ; to-print
```

Before running the test suite, uncomment line 157.  Then, from the command line run
```
$ lein test
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
