### Unit testing strategy for Gizmoball

- junit 5 

- focus on tests that test domain specific (in this case Gizmoball) code. 
this is more likely to change while simple code is not going
to change and also theyre likely to be correct impl

#### Test cases identification

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

- 100% line coverage is a must

- one assert per test

- use the overloaded version of the assert methods, to pass in a message 

- rely on equals and hashcode impl of methods

- run the tests whenever you add new code

- CI pipeline to run tests when pushing to master


