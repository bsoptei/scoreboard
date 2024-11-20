# The scoreboard library

## Approach
- The record Match is used to store information about matches. It is immutable by default. Validation of inputs is done before creating new instances.
- Operations are reflected in the ScoreBoard interface.
- There are two basic implementations, both use in-memory representation. One of them is more flexible to be reused in different scenarios. The other one (compositing the former) is specific to the world cup, accepting team names compatible with FIFA list of countries.
- The creation of concrete instances are controlled via the factory. The implementations are package private in order to hide internal details of the library.
- Testing is done via the TestNG library, which was chosen because it's modern and convenient.
- Library implementations do not depend on external libraries in order to make dependency management easier.
- It is assumed that library components will be used in a multithreaded environment.
- Helpers are added to enable human readable formatting of the outputs.
- Public entities are documented via javadoc.

## Tradeoffs
- Match immutability vs. in-place mutation: updating the scores will create new instances, the old ones are replaced in the board. This helps making the data structure safe, and comes with less boilerplate. 
- Factory method update on new types vs. implementation registry: when new ScoreBoard implementations are created, they need to be added to the factory. This is a tradeoff made to avoid the dependency on external libraries, which could help detect all existing implementations of the ScoreBoard interface via reflection.
- Consistency vs. speed: data synchronization is used in some cases, in order to avoid potential inconsistencies in the data.
