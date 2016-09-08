Feature: As a user of any role I can login to ITS portal with valid credentials so that only authorized user can
view the banner on Dashboard page.

@SmokeTest
Scenario Outline: Verify Banner on dashboard

 Given User can navigate to ITS Portal for "<Environment>"
 When Enter Login credentials "<EmailId>" and  "<Password>"
 Then Verify banner is available for authorised "<roles>"
 And Verify user is able to logout
  
 Examples: 
|roles      |      EmailId            |  Password  |Environment|
|Super User    | sandeep.kale@dtsu.net   |  Abc@12345 | DT|
| Manger       | sandeep.kale@dtsdm1.net  |  Abc@12345 |DT|
| Technician   | sandeep.kale@dttech.net |  Abc@1234 |DT|
|Client Site Manager | sandeep.kale@dtcsm.net  |  Abc@12345  |DT| 
|Manager   | sandeep.kale@sksdm.net   |  Abc@12345 | PRE|


@SmokeTest
Scenario Outline: Verify Banner is not visible after deletion

 Given User can navigate to ITS Portal for "<Environment>"
 When Enter Login credentials "<EmailId>" and  "<Password>"
 Then Verify banner is available for authorised "<roles>"
 And Verify banner can be deleted for "<roles>"
 And Verify user is able to logout
 And Enter Login credentials "<EmailId>" and  "<Password>"
 And Verify banner is not present "<roles>" 
  
 Examples: 
|roles      |      EmailId            |  Password  | Environment|
|Manager	|sandeep.kale@dtsdm2.net	|Abc@12345| DT |
|Manager	|sandeep.kale@sksu.net	|Abc@12345| PRE |