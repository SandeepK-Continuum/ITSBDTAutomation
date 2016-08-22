Feature: As a user I can login to ITS portal with valid credentials so that only authorized user have
access to my resources. 

@SmokeTest
Scenario Outline: Verify authorized login

 Given User can navigate to ITS Portal
 When Enter Login credentials "<EmailId>" and  "<Password>"
 Then Verify user is login to ITS portal
 And  Dashboard is displayed by default
 And Verify user is able to logout
 And Update Result to TestRail "<TestCaseId>"
 Examples: 
 |      EmailId                |  Password  |TestCaseId|SheetName|
 | automationBDD@continuum.net |  Abc@12345 | 12345    |fasf|
 
