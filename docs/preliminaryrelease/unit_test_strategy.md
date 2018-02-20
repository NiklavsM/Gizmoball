### Unit testing strategy for Gizmoball

During the development of the preliminary release, as a team, we have realized that
the code base of this project will steadily increase in both size and complexity 
as we keep adding new features. Therefore, we agreed to do unit testing using 
`JUnit 5` as this practice will boost our confidence when either writing new code
or editing previously written code, this being especially important since the code base
is maintained by multiple developers. As a bonus, this will also help us  
confidentely experiment with extra features without braking the basic functionality,
in order to achieve bonus marks.

- focus on tests that test domain specific (in this case Gizmoball) code. 
this is more likely to change while simple code is not going
to change and also theyre likely to be correct impl

#### Test cases identification

We will focus on writing unit tests for the domain specific code, that is code that 
implements "business logic" (in this case, Gizmoball basic functionality) as this
 is very likely to change a lot and is also crucial in accurately implementing the 
 main requirements.
 
 We are planning to identify test cases mostly based on the `model` side of our `MVC` as these
 classes are responsible to model the physical aspects and interactions of the game.
 
 We are going to have one `JUnit` test class for each of the `model` classes that we are 
 planning to test. The test classes will be placed in the `test` package which is going 
 to have the same structure as the `src` package, for consistency.
 
 ##### What we'll test
 
 - every single equals method 

- do not test library code, assume it works correctly, the same with
other external resources(when theyre available), trivial code like getters and setters,
code that deals only with UI, swing toolkit etc., or non deterministic results

- test class for every model class

- identify test cases based on method spec

- test for checked exceptions being thrown accordingly

- properly test equals and hashcode

- also aim edge cases

#### Approach

- probably both black and white box testing?

- we'll all write tests, but I would say write test for 
code implemented by someone else

- no need for mocking 

- test package will have the same structure as the source package

- test naming conversion 

- TDD? or something else

- 100% line coverage is a must, with regards to business logic code

- one assert per test, unless we use the new junit 5 grouped assertions that reports

- use the overloaded version of the assert methods, to pass in a message 

- rely on equals and hashcode impl of methods

- run the tests whenever you add new code

- CI pipeline to run tests when pushing to master


