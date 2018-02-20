### Unit testing strategy for Gizmoball

During the development of the preliminary release, as a team, we have realized that
the code base of this project will steadily increase in both size and complexity 
as we keep adding new features. Therefore, we agreed to do unit testing using 
`JUnit 5` as this practice will boost our confidence when either writing new code
or editing previously written code, this being especially important when the code base
is maintained by multiple developers.

- junit 5 

- focus on tests that test domain specific (in this case Gizmoball) code. 
this is more likely to change while simple code is not going
to change and also theyre likely to be correct impl

#### Test cases identification

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


