Feature: As a user of any role I can login to ITS portal with valid credentials so that only authorized user can
view the banner on Dashboard page.

@SmokeTest
Scenario Outline: Verify authorized login

 Given User can navigate to ITS Portal
 When Enter Login credentials "<EmailId>" and  "<Password>"
 Then Verify user is login to ITS portal
 And  Dashboard is displayed by default
 And verify banner is available for authorised "<roles>"
 And verify if banner is removed from dashboard then banner should not be displayed after relogin
  And Verify user is able to logout
  
 Examples: 
|roles      |      EmailId            |  Password  |
|Super User    | sandeep.kale@dtsu.net  |  Abc@12345 | 