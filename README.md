# Exercise #1

## Overview

This repo contains the following `.java` files:
- `Ex1` - most of the functions were written by me (the rest were provided to us.)
- `Ex1Test` - I added our my own JUnit tests to all the functions (the preexisting tests were unchanged.)
- `Ex1_GUI` - GUI provided to us (unchanged.)
- `StdDraw` - drawing library provided to us (unchanged.)

We also have:
- `PseudoCode.txt` - pseudocode for all the functions I wrote.
- `output.png` - a screenshot of the output (see below.)

## Execution

To run the project, run (from `I2CS_Ex1/src/`):
```bash
javac Ex1.java Ex1_GUI.java StdDraw.java
java Ex1_GUI
```

> [!NOTE]
> I haven't downloaded the JUnit jar, so I've only run `Ex1Test.java` directly from my IDE.

## Output

The following is a screenshot of the output of `Ex1_GUI`:

![Ex1_GUI screenshot](output.png)

## TODO:
- [x] Write pseudocode for all the functions
- [x] Implement tests for all functions
- [x] Actually write the code
- [x] Write documentation
- [x] Write README
- [x] Add screenshot

## Personal notes

In the previous assignment, I included my pseudocode inside the function bodies. This time, I've written it in a separate file.

I wrote my tests BEFORE implementing the code. This was a new, yet fruitful experience. I also tried to add many edge cases to the test suite.

> [!NOTE]
> I prefer the style of "early returns" (as I find it easier to read than nesting.)
